package com.sm.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.sm.model.Likes;
import com.sm.service.LikeService;
import com.sm.util.MedsolResponse;

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

	/**
	 * @author swarupb
	 * 
	 * 
	 * @param postId
	 * @param userId
	 * @return
	 * @throws PostNotFoundException
	 */
	@PostMapping("/like/{postId}/{userId}")
	public MedsolResponse<Likes> createPostLike(@PathVariable long postId, @PathVariable long userId) throws PostNotFoundException{
		return likeService.createLike(postId, userId);
	}

	/**
	 * @author swarupb
	 * 
	 * @param postId
	 * @param userId
	 * @return
	 * @throws PostNotFoundException
	 */
	@PutMapping("/unLike/{postId}/{userId}")
	public MedsolResponse<Likes> unlikePost(@PathVariable long postId, @PathVariable long userId) throws PostNotFoundException{
		return likeService.postUnlike(postId, userId);
		
	}

	
	/**
	 * @author swarupb
	 * 
	 * @param commentId
	 * @param userId
	 * @return
	 */
	@PostMapping("/comment/like/{commentId}/{userId}")
	public MedsolResponse<Likes> createCommentLike(@PathVariable long commentId, @PathVariable long userId){
		return likeService.createCmtLike(commentId, userId);
	}


	
	/**
	 * @author swarupb
	 * 
	 * @param commentId
	 * @param userId
	 * @return
	 * @throws PostNotFoundException
	 */
	@PutMapping("/comment/unLike/{commentId}/{userId}")
	public MedsolResponse<Likes> unlikeComment(@PathVariable long commentId, @PathVariable long userId) throws PostNotFoundException{
		return likeService.unlikeCmt(commentId, userId);
		
	}

}
