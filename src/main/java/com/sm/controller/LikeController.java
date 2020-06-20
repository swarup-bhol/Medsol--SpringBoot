package com.sm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.dao.CommentDao;
import com.sm.dao.LikeDao;
import com.sm.dao.PostDao;
import com.sm.dao.UserDao;
import com.sm.exception.PostNotFoundException;
import com.sm.exception.ResourceNotFoundException;
import com.sm.model.Comment;
import com.sm.model.Likes;
import com.sm.model.Post;
import com.sm.model.User;
import com.sm.service.LikeService;
import com.sm.util.ApiResponse;
import com.sm.util.Constants;

@RestController
@RequestMapping("/api/medsol")
public class LikeController {
	
	@Autowired
	PostDao postDao;
	
	@Autowired
	UserDao userDao;
	 
	@Autowired
	LikeDao  likeDao;
	
	@Autowired
	LikeService likeService;
	
	@Autowired
	CommentDao commentDao;

	@PostMapping("/like/{postId}/{userId}")
	public ApiResponse<Likes> createPostLike(@PathVariable long postId, @PathVariable long userId) throws PostNotFoundException{
		Post post = postDao.findByPostId(postId);
		if(post == null) throw new PostNotFoundException(Constants.RESOURCE_NOT_FOUND);
		User user = userDao.findByUserId(userId);
		if(user == null ) throw new UsernameNotFoundException(Constants.USER_NOT_FOUND);
		Likes like = likeService.createNewLike(user, post);
		return new ApiResponse<>(200, Constants.OK, like);
	}
	
	@PutMapping("/unLike/{postId}/{userId}")
	public ApiResponse<Likes> unlikePost(@PathVariable long postId, @PathVariable long userId) throws PostNotFoundException{
		Post post = postDao.findByPostId(postId);
		if(post == null) throw new PostNotFoundException(Constants.RESOURCE_NOT_FOUND);
		User user = userDao.findByUserId(userId);
		if(user == null ) throw new UsernameNotFoundException(Constants.USER_NOT_FOUND);
		Likes like = likeService.updateUnlike(user, post);
		return new ApiResponse<>(200, Constants.OK, like);
		
	}
	
	@PostMapping("/comment/like/{commentId}/{userId}")
	public ApiResponse<Likes> createCommentLike(@PathVariable long commentId, @PathVariable long userId){
		Comment comment = commentDao.findByCommentId(commentId);
		if(comment == null ) throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
		User user = userDao.findByUserId(userId);
		if(user == null) throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
		Likes commentLike = likeService.createNewCommentLike(comment,user);
		return new ApiResponse<>(200, Constants.OK, commentLike);
	}
	
	@PutMapping("/comment/unLike/{commentId}/{userId}")
	public ApiResponse<Likes> unlikeComment(@PathVariable long commentId, @PathVariable long userId) throws PostNotFoundException{
		Comment comment = commentDao.findByCommentId(commentId);
		if(comment == null) throw new PostNotFoundException(Constants.RESOURCE_NOT_FOUND);
		User user = userDao.findByUserId(userId);
		if(user == null ) throw new UsernameNotFoundException(Constants.USER_NOT_FOUND);
		 Likes updateCommentUnlike = likeService.updateCommentUnlike(user, comment);
		return new ApiResponse<>(200, Constants.OK, updateCommentUnlike);
		
	}
}
