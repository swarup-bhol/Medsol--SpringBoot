package com.sm.service.impl;

import static java.lang.Math.min;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sm.dao.CommentDao;
import com.sm.dao.LikeDao;
import com.sm.dao.PostDao;
import com.sm.dao.PostTypeDao;
import com.sm.dao.ProfessionDao;
import com.sm.dao.SpecializationDao;
import com.sm.dao.UserDao;
import com.sm.dto.CommentListDto;
import com.sm.dto.PostDto;
import com.sm.dto.ReplayListCommentDto;
import com.sm.exception.FileFormatException;
import com.sm.exception.PostNotFoundException;
import com.sm.exception.ResourceNotFoundException;
import com.sm.exception.UserNotFound;
import com.sm.model.Comment;
import com.sm.model.Likes;
import com.sm.model.Post;
import com.sm.model.PostType;
import com.sm.model.Profession;
import com.sm.model.Specialization;
import com.sm.model.User;
import com.sm.service.PostService;
import com.sm.service.UserService;
import com.sm.util.Constants;
import com.sm.util.MedsolResponse;


@Service
public class PostServiceImpl implements PostService {

	public static final String uploadingDir = System.getProperty("user.dir") + "/Uploads/Posts";
	public static final String VideoUploadingDir = System.getProperty("user.dir") + "/Uploads/Posts/Videos";
	private static final long CHUNK_SIZE = 1000000L;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PostDao postdao;

	@Autowired
	CommentDao commentdao;

	@Autowired
	UserService userService;

	@Autowired
	UserDao userDao;

	@Autowired
	LikeDao likeDao;

	@Autowired
	ProfessionDao profDao;

	@Autowired
	PostTypeDao postTypeDao;

	@Autowired
	SpecializationDao specializationDao;

	/**
	 * 
	 */
	@Override
	public Post uploadMedia(MultipartFile file, User user, String content, String type) throws IOException {

		Post savePost = null;
		try {
			String extentions = FilenameUtils.getExtension(file.getOriginalFilename());
			Post post = new Post();
			post.setPostContent(content);
			if (extentions.equals("mp4") || extentions.equals("3gp") || extentions.equals("mkv")) {
				if (!new File(VideoUploadingDir).exists()) {
					new File(VideoUploadingDir).mkdirs();
				}
				Path videoUploadPath = Paths.get(VideoUploadingDir, file.getOriginalFilename());
				Files.write(videoUploadPath, file.getBytes());
				post.setPostVideoPath(videoUploadPath.toString());
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
			savePost = postdao.save(post);
			updatePostType(savePost, type);
		} catch (HibernateException e) {
			logger.error("Db Error : uploadMedia", e.getMessage());
		} catch (IOException e) {
			logger.error("IO Error : uploadMedia", e.getMessage());
		} catch (FileFormatException e) {
			logger.error("FileFormat Error : uploadMedia", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : uploadMedia", e.getMessage());
		}
		return savePost;
	}

	@Override
	public Post createPost(User user, String content, String type) {

		Post post = null;
		try {
			post = new Post();
			post.setPostContent(content);
			post.setUser(user);
			post.setRecordStatus(true);
			post = postdao.save(post);
			updatePostType(post, type);
		} catch (HibernateException e) {
			logger.error("Db Error : createPost", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null Error : createPost", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : createPost", e.getMessage());
		}
		return post;
	}

	@Override
	public Post getPostById(long postId) {
		Post post = null;
		try {
			post = postdao.findByPostId(postId);
		} catch (HibernateException e) {
			logger.error("Db Error : getPostById", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : getPostById", e.getMessage());
		}
		return post;

	}

	@Override
	public Post updateMedia(Post post, String content, MultipartFile file) throws IOException {
		Post post2 = null;
		try {
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
			post2 = postdao.save(post);
		} catch (HibernateException e) {
			logger.error("Db Error : updateMedia", e.getMessage());
		} catch (IOException e) {
			logger.error("IO Error : updateMedia", e.getMessage());
		} catch (FileFormatException e) {
			logger.error("FileFormat Error : updateMedia", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : updateMedia", e.getMessage());
		}
		return post2;
	}

	@Override
	public List<PostDto> getNewsFeedPosts(User user, int pageNo) {

		List<Post> posts = null;
		List<PostDto> preparePosts = new ArrayList<PostDto>();
		try {
			posts = postdao.findAllByRecordStatusOrderByPostIdDesc(true, PageRequest.of(pageNo, 10));
			if (!posts.isEmpty()) {
				preparePosts = preparePosts(posts, user);
			}
		} catch (HibernateException e) {
			logger.error("Db Error : getNewsFeedPosts", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null Error : getNewsFeedPosts", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : getNewsFeedPosts", e.getMessage());
		}
		return preparePosts;
	}

	@Override
	public List<PostDto> getPostSpecType(List<Long> specList, User user, int pageNo) {
		List<Specialization> allSpecById = new ArrayList<Specialization>();
		List<PostDto> preparePosts = new ArrayList<PostDto>();
		try {
			allSpecById = specializationDao.findBySpecializationIdIn(specList);
			if (allSpecById.isEmpty()) {
				throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
			}
			List<Post> posts = postTypeDao.findPostBySpecializationIn(allSpecById, PageRequest.of(pageNo, 10));
			if (!posts.isEmpty()) {
				preparePosts = preparePosts(posts, user);
			}
		} catch (HibernateException e) {
			logger.error("Db Error : getPostSpecType", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null Error : getPostSpecType", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : getPostSpecType", e.getMessage());
		}
		return preparePosts;
	}

	@Override
	public List<PostDto> getUploadedPost(User user, int pageNo) {
		List<Post> posts = new ArrayList<Post>();
		List<PostDto> preparePosts = new ArrayList<PostDto>();
		try {
			posts = postdao.findAllByUserAndRecordStatusOrderByPostIdDesc(user, true, PageRequest.of(pageNo, 10));
			if (!posts.isEmpty()) {
				preparePosts = preparePosts(posts, user);
			}
		} catch (HibernateException e) {
			logger.error("Db Error : getUploadedPost", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null Error : getUploadedPost", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : getUploadedPost", e.getMessage());
		}
		return preparePosts;

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

		try {
			postDtos = posts.stream().map(post -> {
				List<Comment> comments = commentdao.findByPostAndReCommentId(post, 0);
				long likeCount = likeDao.countByPost(post);
				Likes likes = likeDao.findByPostAndUserAndCommentId(post, user, 0);
				Profession profession = profDao.findByProfessionId(post.getUser().getProfessionId());
				return PostDto.builder().commentLIst(prepareComment(comments, user)).post(post).likeCount(likeCount)
						.userId(post.getUser().getUserId()).fullName(post.getUser().getFullName())
						.instituteName(post.getUser().getInstituteName()).commentCount(comments.size())
						.Profession(profession.getProfessionName()).isLike(checkLike(likes)).build();
			}).collect(Collectors.toList());

		} catch (HibernateException e) {
			logger.error("Db Error : preparePosts", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null Error : preparePosts", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : preparePosts", e.getMessage());
		}
		return postDtos;

	}

	private boolean checkLike(Likes likes) {
		if (likes == null)
			return false;
		else
			return likes.isRecordStatus();
	}

	private List<CommentListDto> prepareComment(List<Comment> comments, User user) {
		List<CommentListDto> listDtos = new ArrayList<CommentListDto>();

		try {
			listDtos = comments.stream().map(comt -> {
				return CommentListDto.builder().userId(comt.getUser().getUserId())
						.userName(comt.getUser().getFullName()).commentId(comt.getCommentId())
						.commentedText(comt.getComment()).replays(prepareReplay(comt, user))
						.commentedTime(comt.getCreatedTime()).likeCount(likeDao.countByCommentId(comt.getCommentId()))
						.isLike(isLike(comt.getPost(), user, comt.getCommentId())).build();
			}).collect(Collectors.toList());
		} catch (HibernateException e) {
			logger.error("Db Error : prepareComment", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null Error : prepareComment", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : prepareComment", e.getMessage());
		}
		return listDtos;
	}

	private boolean isLike(Post post, User user, long commentId) {
		boolean islike = false;
		Likes commentLike = null;
		try {
			commentLike = likeDao.findByPostAndUserAndCommentId(post, user, commentId);
			if (commentLike != null) {
				islike = commentLike.isRecordStatus();
			} else {
				islike = false;
			}
		} catch (HibernateException e) {
			logger.error("Db Error : prepareComment", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : prepareComment", e.getMessage());
		}
		return islike;
	}

	private List<ReplayListCommentDto> prepareReplay(Comment comment, User user) {
		List<ReplayListCommentDto> listCommentDtos = new ArrayList<ReplayListCommentDto>();
		List<Comment> findByReCommentId = new ArrayList<Comment>();

		try {
			findByReCommentId = commentdao.findByReCommentId(comment.getCommentId());
			listCommentDtos = findByReCommentId.stream().map(comt -> {
				return ReplayListCommentDto.builder().commentedText(comt.getComment()).commentId(comt.getCommentId())
						.userId(comt.getUser().getUserId()).userName(comt.getUser().getFullName())
						.commentedTime(comt.getCreatedTime())
						.likeCount(likeDao.countByCommentId(comment.getCommentId()))
						.isLike(isLike(comt.getPost(), user, comt.getCommentId())).build();
			}).collect(Collectors.toList());
		} catch (HibernateException e) {
			logger.error("Db Error : prepareReplay", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null Error : prepareReplay", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : prepareReplay", e.getMessage());
		}
		return listCommentDtos;
	}

	@Override
	public PostDto findByPostId(Post post) {
		PostDto postDto = new PostDto();
		List<Comment> comments = new ArrayList<Comment>();

		try {
			comments = commentdao.findByPostAndReCommentId(post, 0);
			long likeCount = likeDao.countByPost(post);
			Likes likes = likeDao.findByPostAndUserAndCommentId(post, post.getUser(), 0);
			Profession profession = profDao.findByProfessionId(post.getUser().getProfessionId());

			postDto = PostDto.builder().commentLIst(prepareComment(comments, post.getUser())).post(post)
					.likeCount(likeCount).userId(post.getUser().getUserId()).fullName(post.getUser().getFullName())
					.instituteName(post.getUser().getInstituteName()).commentCount(comments.size())
					.Profession(profession.getProfessionName()).isLike(checkLike(likes)).build();
		} catch (HibernateException e) {
			logger.error("Db Error : findByPostId", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null Error : findByPostId", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : findByPostId", e.getMessage());
		}
		return postDto;
	}

	private void updatePostType(Post savePost, String type) {
		List<Specialization> specializations = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			specializations = mapper.readValue(type,
					mapper.getTypeFactory().constructCollectionType(List.class, Specialization.class));
			List<PostType> postTypes = new ArrayList<PostType>();
			Iterator<Specialization> iterator = specializations.iterator();
			while (iterator.hasNext()) {
				PostType postType = new PostType();
				postType.setPost(savePost);
				postType.setSpecialization(iterator.next());
				postTypes.add(postType);
			}
			postTypeDao.saveAll(postTypes);
		} catch (JsonProcessingException e) {
			logger.error("Cannot convert to List  Object : updatePostType");
		} catch (NullPointerException e) {
			logger.error("Null Error : updatePostType", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : updatePostType", e.getMessage());
		}

	}

	@Override
	public ResponseEntity<ResourceRegion> getVideoRegion(Post post, String rangeHeader) throws IOException {
		FileUrlResource videoResource = new FileUrlResource(post.getPostVideoPath());
		ResourceRegion resourceRegion = getResourceRegion(videoResource, rangeHeader);
		return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
				.contentType(MediaTypeFactory.getMediaType(videoResource).orElse(MediaType.APPLICATION_OCTET_STREAM))
				.body(resourceRegion);
	}

	private ResourceRegion getResourceRegion(UrlResource video, String httpHeaders) throws IOException {
		ResourceRegion resourceRegion = null;

		long contentLength = video.contentLength();
		int fromRange = 0;
		int toRange = 0;
		if (StringUtils.isNotBlank(httpHeaders)) {
			String[] ranges = httpHeaders.substring("bytes=".length()).split("-");
			fromRange = Integer.valueOf(ranges[0]);
			if (ranges.length > 1) {
				toRange = Integer.valueOf(ranges[1]);
			} else {
				toRange = (int) (contentLength - 1);
			}
		}

		if (fromRange > 0) {
			long rangeLength = min(CHUNK_SIZE, toRange - fromRange + 1);
			resourceRegion = new ResourceRegion(video, fromRange, rangeLength);
		} else {
			long rangeLength = min(CHUNK_SIZE, contentLength);
			resourceRegion = new ResourceRegion(video, 0, rangeLength);
		}

		return resourceRegion;
	}

	@Override
	public MedsolResponse<Post> createNewPost(Long userId, String content, MultipartFile file, String type) {
		MedsolResponse<Post> response = new MedsolResponse<>(true, 200, Constants.CREATED, null);
		try {
			if (content.isEmpty())
				response = new MedsolResponse<>(true, 400, Constants.BAD_REQUEST, content);
			User user = userService.findByuserId(userId);
			if (user == null)
				throw new UserNotFound(Constants.USER_NOT_FOUND);
			if (file == null) { // Condition for only uploading text Post
				response = new MedsolResponse<>(true, 200, Constants.CREATED, createPost(user, content, type));
			}
			response = new MedsolResponse<>(true, 200, Constants.CREATED, uploadMedia(file, user, content, type));
		} catch (IOException e) {
			logger.error("Error : createNewPost", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : createNewPost", e.getMessage());
		}
		return response;
	}

	@Override
	public MedsolResponse<Post> updatePost(Long postId, String content, MultipartFile file) {

		MedsolResponse<Post> response = new MedsolResponse<>(true, 400, Constants.BAD_REQUEST, content);
		try {
			if (content.isEmpty())
				response = new MedsolResponse<>(true, 400, Constants.BAD_REQUEST, content);
			Post post = getPostById(postId);
			if (post == null)
				response = new MedsolResponse<Post>(true, 204, Constants.FILE_NOT_FOUND, post);
			if (file == null) {
				post.setPostContent(content);
				response = new MedsolResponse<>(true, 200, Constants.CREATED, postdao.save(post));
			}
			response = new MedsolResponse<>(true, 200, Constants.CREATED, updateMedia(post, content, file));
		} catch (IOException e) {
			logger.error("IO : updatePost", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : updatePost", e.getMessage());
		}
		return response;
	}

	@Override
	public MedsolResponse<Post> deletePosts(long postId) {
		try {
			Post post = postdao.findByPostId(postId);
			if (post == null)
				throw new PostNotFoundException(Constants.RESOURCE_NOT_FOUND);
			post.setRecordStatus(false);
			postdao.save(post);
		} catch (HibernateException e) {
			logger.error("Error : updatePost", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : updatePost", e.getMessage());
		}
		return new MedsolResponse<>(true, 200, Constants.DELETED, postId);
	}

	@Override
	public MedsolResponse<List<PostDto>> postByUser(long userId, int pageNo) {
		MedsolResponse<List<PostDto>> response = new MedsolResponse<>(false, 200, Constants.OK, null);
		try {
			User user = userService.findByuserId(userId);
			if (user == null)
				throw new UserNotFound(Constants.USER_NOT_FOUND);
			response = new MedsolResponse<>(true, 200, Constants.OK, getUploadedPost(user, pageNo));
		} catch (Exception e) {
			logger.error("Error : postByUser", e.getMessage());
		}
		return response;
	}

	@Override
	public MedsolResponse<List<PostDto>> allPosts(long userId, int pageNo) {
		MedsolResponse<List<PostDto>> response = new MedsolResponse<>(false, 200, Constants.OK, null);
		try {
			User user = userDao.findByUserId(userId);
			if (user == null)
				throw new UserNotFound(Constants.USER_NOT_FOUND);
			response = new MedsolResponse<>(true, 200, Constants.OK, getNewsFeedPosts(user, pageNo));
		} catch (HibernateException e) {
			logger.error("Error : allPosts", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : allPosts", e.getMessage());
		}
		return response;
	}

	@Override
	public MedsolResponse<PostDto> postBySpec(List<Long> specList, long userId, int pageNo) {
		MedsolResponse<PostDto> response = new MedsolResponse<PostDto>(false, 200, Constants.OK, null);
		try {
			User user = userDao.findByUserId(userId);
			List<PostDto> postDtos = getPostSpecType(specList, user, pageNo);
			response = new MedsolResponse<PostDto>(true, 200, Constants.OK, postDtos);
		} catch (HibernateException e) {
			logger.error("Error : postBySpec", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : postBySpec", e.getMessage());
		}
		return response;
	}

	@Override
	public MedsolResponse<PostDto> postsById(long postId) {
		MedsolResponse<PostDto> response = new MedsolResponse<>(false, 200, Constants.OK, null);
		try {
			Post post = postdao.findByPostId(postId);
			if (post == null)
				throw new ResourceNotFoundException(Constants.FILE_NOT_FOUND);
			PostDto postDto = findByPostId(post);
			response = new MedsolResponse<>(true, 200, Constants.OK, postDto);
		} catch (HibernateException e) {
			logger.error("Error : postsById", e.getMessage());
		} catch (Exception e) {
			logger.error("Error : postsById", e.getMessage());
		}
		return response;
	}

}
