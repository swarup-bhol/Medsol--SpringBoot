package com.sm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sm.dao.CommentDao;
import com.sm.dao.PostDao;
import com.sm.dto.CommentDto;
import com.sm.dto.CommentListDto;
import com.sm.exception.ResourceNotFoundException;
import com.sm.model.Comment;
import com.sm.model.Post;
import com.sm.model.User;
import com.sm.service.CommentService;
import com.sm.service.UserService;
import com.sm.util.Constants;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	UserService userService;
	
	@Autowired
	PostDao postDao;
	
	@Autowired
	CommentDao commentDao;

	@Override
	public CommentListDto createNewComment(CommentDto commentDto) {
		Post post = postDao.findByPostId(commentDto.getPostId());
		if(post == null) throw new ResourceNotFoundException("Post Not found");
		
		User user = userService.findByuserId(commentDto.getUserId());
		if(user == null) throw new UsernameNotFoundException(Constants.USER_NOT_FOUND);
		
		Comment comment = new Comment();
		comment.setComment(commentDto.getMessage());
		comment.setUser(user);
		comment.setPost(post);
		Comment cmt = commentDao.save(comment);
		CommentListDto dtos =new CommentListDto(); 
		dtos.setUserId(user.getUserId());
		dtos.setCommentedTime(cmt.getCreatedTime());
		dtos.setCommentedText(cmt.getComment());
		dtos.setCommentId(cmt.getCommentId());
		dtos.setUserName(user.getFullName());
		return dtos;
	}

//	@Override
//	public Comment createNewComment(User user, Post post, String comments) {
//		Comment comment = new Comment();
//		comment.setComment(comments);
//		comment.setUser(user);
//		comment.setPost(post);
//		return commentDao.save(comment);
//	}
	
	

}
