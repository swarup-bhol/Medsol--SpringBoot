package com.sm.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sm.dao.CommentDao;
import com.sm.dao.LikeDao;
import com.sm.dao.PostDao;
import com.sm.dao.ProfessionDao;
import com.sm.dto.CommentListDto;
import com.sm.dto.PostDto;
import com.sm.dto.ReplayListCommentDto;
import com.sm.model.Comment;
import com.sm.model.Likes;
import com.sm.model.Post;
import com.sm.model.Profession;
import com.sm.model.User;
import com.sm.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	public static final String uploadingDir = System.getProperty("user.dir") + "/Uploads/Posts";

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
		if (!new File(uploadingDir).exists()) {
			new File(uploadingDir).mkdirs();
		}
		Path uploadPath = Paths.get(uploadingDir, file.getOriginalFilename());
		Files.write(uploadPath, file.getBytes());

		Post post = new Post();
		post.setPostContent(content);
		post.setPostImgPath(uploadPath.toString());
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

//	private List<ReplayListCommentDto> prepareReplay(Comment comment){
//		List<ReplayListCommentDto> listCommentDtos =  new ArrayList<ReplayListCommentDto>();
//		List<ReComment> replayComments = reCommentDao.findByComment(comment);
//		Iterator<ReComment> iterator = replayComments.iterator();
//		while(iterator.hasNext()) {
//			ReComment next = iterator.next();
//			ReplayListCommentDto commentDto = new ReplayListCommentDto();
//			commentDto.setCommentedText(next.getReComment());
//			commentDto.setCommentId(next.getReCommentId());
//			commentDto.setUserId(next.getUser().getUserId());
//			commentDto.setUserName(next.getUser().getFullName());
//			commentDto.setCommentedTime(next.getCreatedTime());
//			listCommentDtos.add(commentDto);
//		}
//		return listCommentDtos;
//	}

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

}
