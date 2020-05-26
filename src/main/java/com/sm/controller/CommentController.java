package com.sm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.dao.CommentDao;
import com.sm.dao.PostDao;
import com.sm.dto.CommentDto;
import com.sm.dto.CommentListDto;
import com.sm.exception.ResourceNotFoundException;
import com.sm.model.Comment;
import com.sm.model.Post;
import com.sm.service.CommentService;
import com.sm.util.ApiResponse;
import com.sm.util.Constants;


@RequestMapping("/api/medsol/comment")
@RestController
public class CommentController {
	
	@Autowired
	PostDao postDao;
	
	@Autowired
	CommentDao commentDao; 
	
	@Autowired
	CommentService commentService;
	
	
	@PostMapping("/create")
	public ApiResponse<Comment> postComment(@RequestBody CommentDto cmmnt){
		CommentListDto comment = commentService.createNewComment(cmmnt);
		return new ApiResponse<>( 200, Constants.OK, comment);
		
	}
	@GetMapping("/{postId}")
	public ApiResponse<List<Comment>> getPostComment(@PathVariable long postId){
		Post post = postDao.findByPostId(postId);
		if(post == null) throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
		List<Comment> allComments = commentDao.findByPost(post);
		return new ApiResponse<>(200, Constants.OK, allComments);
	}
	
	@DeleteMapping("/{commentId}")
	public ApiResponse<String> removeComment(@PathVariable long commentId){
		Comment comment = commentDao.findByCommentId(commentId);
		if(comment == null) throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
		commentDao.deleteById(commentId);
		return new ApiResponse<>(200, Constants.OK, Constants.DELETED);
	}
	
	@PutMapping("/{commentId}")
	public ApiResponse<Comment> updateComment(@RequestBody CommentDto commentDto,@PathVariable long commentId){		
		Comment comment = commentDao.findByCommentId(commentId);
		if(comment == null) throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
		comment.setComment(commentDto.getMessage());
		return new ApiResponse<>(200, Constants.OK, commentDao.save(comment));
	}

}
