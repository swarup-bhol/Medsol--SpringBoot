package com.sm.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sm.dao.CommentDao;
import com.sm.dao.LikeDao;
import com.sm.dao.PostDao;
import com.sm.dao.ProfessionDao;
import com.sm.dto.CommentListDto;
import com.sm.dto.PostDto;
import com.sm.dto.ReplayListCommentDto;
import com.sm.exception.FileFormatException;
import com.sm.exception.PostNotFoundException;
import com.sm.model.Comment;
import com.sm.model.Likes;
import com.sm.model.Post;
import com.sm.model.Profession;
import com.sm.model.User;
import com.sm.service.PostService;
import com.sm.util.Constants;

@Service
public class PostServiceImpl implements PostService {

	public static final String uploadingDir = System.getProperty("user.dir") + "/Uploads/Posts";
	public static final String VideoUploadingDir = System.getProperty("user.dir") + "/Uploads/Posts/Videos";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PostDao postdao;

	@Autowired
	CommentDao commentdao;

	@Autowired
	LikeDao likeDao;

	@Autowired
	ProfessionDao profDao;

	/**
	 * 
	 */
	@Override
	public Post uploadMedia(MultipartFile file, User user, String content) throws IOException {

		String extentions = FilenameUtils.getExtension(file.getOriginalFilename());
		Post post = new Post();
		post.setPostContent(content);
		if (extentions.equals("mp4") || extentions.equals("3gp") || extentions.equals("mkv")) {
			if (!new File(VideoUploadingDir).exists()) {
				new File(VideoUploadingDir).mkdirs();
			}
			Path videoUploadPath = Paths.get(VideoUploadingDir, file.getOriginalFilename());
			Files.write(videoUploadPath, file.getBytes());
			post.setPostVideoPath(VideoUploadingDir.toString());
		} else if (extentions.equals("png") || extentions.equals("jpg") || extentions.equals("jpeg")
				|| extentions.equals("webp")) {
			if (!new File(uploadingDir).exists()) {
				new File(uploadingDir).mkdirs();
			}

			Path uploadPath = Paths.get(uploadingDir, file.getOriginalFilename());
			Files.write(uploadPath, file.getBytes());
			post.setPostImgPath(uploadPath.toString());
		} else {
			logger.info(Constants.INVALID_FILE_FORMAT);
			throw new FileFormatException(Constants.INVALID_FILE_FORMAT);
		}

		post.setUser(user);
		post.setRecordStatus(true);
		return postdao.save(post);
	}

	@Override
	public Post createPost(User user, String content) {

		Post post = new Post();
		post.setPostContent(content);
		post.setUser(user);
		post.setRecordStatus(true);
		return postdao.save(post);
	}

	@Override
	public Post getPostById(long postId) {
		return postdao.findByPostId(postId);

	}

	@Override
	public Post updateMedia(Post post, String content, MultipartFile file) throws IOException {
		String uploadDirectory = post.getPostImgPath();
		if (uploadDirectory.contains(file.getOriginalFilename())) {
			Path uploadPath = Paths.get(uploadDirectory);
			Files.write(uploadPath, file.getBytes());
			post.setPostContent(content);
			return postdao.save(post);
		}
		File img = new File(uploadDirectory);
		img.delete();
		Path uploadPath = Paths.get(uploadingDir, file.getOriginalFilename());
		Files.write(uploadPath, file.getBytes());
		post.setPostImgPath(uploadPath.toString());
		post.setPostContent(content);
		return postdao.save(post);
	}

	@Override
	public List<PostDto> getNewsFeedPosts(User user, int pageNo) {

		List<Post> posts = postdao.findAllByRecordStatusOrderByPostIdDesc(true, PageRequest.of(pageNo, 10));
		if (posts.isEmpty()) {
			return new ArrayList<>();
		} else {

			return preparePosts(posts, user);
		}
	}

	@Override
	public List<PostDto> getUploadedPost(User user, int pageNo) {
		List<Post> posts = postdao.findAllByUserAndRecordStatusOrderByPostIdDesc(user, true,
				PageRequest.of(pageNo, 10));
//		List<Post> posts = postdao.findAllByRecordStatusAndUser(user, true);
		if (posts.isEmpty()) {
			return new ArrayList<>();
		} else {
			return preparePosts(posts, user);
		}

	}

	/**
	 * @author swarup
	 * 
	 * 
	 * @param posts
	 * @param user
	 * @return List
	 */
	private List<PostDto> preparePosts(List<Post> posts, User user) {
		List<PostDto> postDtos = new ArrayList<PostDto>();

		Iterator<Post> itr = posts.iterator();
		while (itr.hasNext()) {
			Post post = itr.next();
			PostDto postDto = new PostDto();
			List<Comment> comments = commentdao.findByPostAndReCommentId(post, 0);
			long likeCount = likeDao.countByPost(post);
			Likes likes = likeDao.findByPostAndUserAndCommentId(post, user, 0);
			Profession profession = profDao.findByProfessionId(post.getUser().getProfessionId());
			postDto.setCommentLIst(prepareComment(comments, user));
			postDto.setPost(post);
			postDto.setLikeCount(likeCount);
			postDto.setUserId(post.getUser().getUserId());
			postDto.setFullName(post.getUser().getFullName());
			postDto.setInstituteName(post.getUser().getInstituteName());
			postDto.setCommentCount(comments.size());
			postDto.setProfession(profession.getProfessionName());
			if (likes == null)
				postDto.setLike(false);
			else
				postDto.setLike(likes.isRecordStatus());
			postDtos.add(postDto);
		}
		return postDtos;

	}

	private List<CommentListDto> prepareComment(List<Comment> comments, User user) {

		Iterator<Comment> itr = comments.iterator();
		List<CommentListDto> listDtos = new ArrayList<CommentListDto>();
		while (itr.hasNext()) {
			Comment comt = itr.next();
			CommentListDto dto = new CommentListDto();
			dto.setUserId(comt.getUser().getUserId());
			dto.setUserName(comt.getUser().getFullName());
			dto.setCommentId(comt.getCommentId());
			dto.setCommentedText(comt.getComment());
			dto.setReplays(prepareReplay(comt, user));
			dto.setCommentedTime(comt.getCreatedTime());
			dto.setLikeCount(likeDao.countByCommentId(comt.getCommentId()));

			Likes commentLike = likeDao.findByPostAndUserAndCommentId(comt.getPost(), user, comt.getCommentId());
			if (commentLike != null) {
				dto.setLike(commentLike.isRecordStatus());
				System.out.println(commentLike.isRecordStatus());
			} else {
				dto.setLike(false);
			}

			listDtos.add(dto);
		}
		return listDtos;
	}

	private List<ReplayListCommentDto> prepareReplay(Comment comment, User user) {
		List<ReplayListCommentDto> listCommentDtos = new ArrayList<ReplayListCommentDto>();
		List<Comment> findByReCommentId = commentdao.findByReCommentId(comment.getCommentId());
		Iterator<Comment> iterator = findByReCommentId.iterator();
		while (iterator.hasNext()) {
			Comment next = iterator.next();
			ReplayListCommentDto commentDto = new ReplayListCommentDto();
			commentDto.setCommentedText(next.getComment());
			commentDto.setCommentId(next.getCommentId());
			commentDto.setUserId(next.getUser().getUserId());
			commentDto.setUserName(next.getUser().getFullName());
			commentDto.setCommentedTime(next.getCreatedTime());
			commentDto.setLikeCount(likeDao.countByCommentId(comment.getCommentId()));
			Likes commentLike = likeDao.findByPostAndUserAndCommentId(comment.getPost(), user, comment.getCommentId());
			if (commentLike != null) {
				commentDto.setLike(commentLike.isRecordStatus());
			} else {
				commentDto.setLike(false);
			}

			listCommentDtos.add(commentDto);
		}
		return listCommentDtos;
	}

	@Override
	public PostDto findByPostId(Post post) {
		PostDto postDto = new PostDto();
		List<Comment> comments = commentdao.findByPostAndReCommentId(post, 0);
		long likeCount = likeDao.countByPost(post);
		Likes likes = likeDao.findByPostAndUserAndCommentId(post, post.getUser(), 0);
		Profession profession = profDao.findByProfessionId(post.getUser().getProfessionId());
		postDto.setCommentLIst(prepareComment(comments, post.getUser()));
		postDto.setPost(post);
		postDto.setLikeCount(likeCount);
		postDto.setUserId(post.getUser().getUserId());
		postDto.setFullName(post.getUser().getFullName());
		postDto.setInstituteName(post.getUser().getInstituteName());
		postDto.setCommentCount(comments.size());
		postDto.setProfession(profession.getProfessionName());
		if (likes == null)
			postDto.setLike(false);
		else
			postDto.setLike(likes.isRecordStatus());
		return postDto;
	}

	/**
	 * 
	 */
	
	@Override
	public ResponseEntity<byte[]> prepareContent(long postId, String range) {
		long rangeStart = 0;
        long rangeEnd;
        byte[] data;
        Long fileSize;
        Post post = postdao.findByPostId(postId);
        if(post == null || post.getPostVideoPath() == null) return null;
        
        File file = new File(post.getPostVideoPath());
        
        String exttension = FilenameUtils.getExtension(post.getPostVideoPath());
        
        try {
            fileSize = getFileSize(file.getAbsolutePath());
            if (range == null) {
                return ResponseEntity.status(HttpStatus.OK)
                        .header(Constants.CONTENT_TYPE, Constants.VIDEO_CONTENT + exttension)
                        .header(Constants.CONTENT_LENGTH, String.valueOf(fileSize))
                        .body(readByteRange(file.getAbsolutePath(), rangeStart, fileSize - 1)); // Read the object and convert it as bytes
            }
            String[] ranges = range.split("-");
            rangeStart = Long.parseLong(ranges[0].substring(6));
            if (ranges.length > 1) {
                rangeEnd = Long.parseLong(ranges[1]);
            } else {
                rangeEnd = fileSize - 1;
            }
            if (fileSize < rangeEnd) {
                rangeEnd = fileSize - 1;
            }
            data = readByteRange(file.getAbsolutePath(), rangeStart, rangeEnd);
        } catch (IOException e) {
            logger.error("Exception while reading the file {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .header(Constants.CONTENT_TYPE, Constants.VIDEO_CONTENT + "mp4")
                .header(Constants.ACCEPT_RANGES, Constants.BYTES)
                .header(Constants.CONTENT_LENGTH, contentLength)
                .header(Constants.CONTENT_RANGE, Constants.BYTES + " " + rangeStart + "-" + rangeEnd + "/" + fileSize)
                .body(data);
	}
	   public byte[] readByteRange(String filename, long start, long end) throws IOException {
	        Path path = Paths.get(filename);
	        try (InputStream inputStream = (Files.newInputStream(path));
	             ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream()) {
	            byte[] data = new byte[Constants.BYTE_RANGE];
	            int nRead;
	            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
	                bufferedOutputStream.write(data, 0, nRead);
	            }
	            bufferedOutputStream.flush();
	            byte[] result = new byte[(int) (end - start) + 1];
	            System.arraycopy(bufferedOutputStream.toByteArray(), (int) start, result, 0, result.length);
	            return result;
	        }
	    }

	    /**
	     * Get the filePath.
	     *
	     * @return String.
	     */
	    private String getFilePath() {
	        URL url = this.getClass().getResource(Constants.VIDEO);
	        return new File(url.getFile()).getAbsolutePath();
	    }

	    /**
	     * Content length.
	     *
	     * @param fileName String.
	     * @return Long.
	     */
	    public Long getFileSize(String fileName) {
	        return Optional.ofNullable(fileName)
	                .map(file -> Paths.get(getFilePath(), file))
	                .map(this::sizeFromFile)
	                .orElse(0L);
	    }

	    /**
	     * Getting the size from the path.
	     *
	     * @param path Path.
	     * @return Long.
	     */
	    private Long sizeFromFile(Path path) {
	        try {
	            return Files.size(path);
	        } catch (IOException ioException) {
	            logger.error("Error while getting the file size", ioException);
	        }
	        return 0L;
	    }

}
