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
import com.sm.dao.UserDao;
import com.sm.dto.CommentDto;
import com.sm.dto.CommentListDto;
import com.sm.dto.ReCommentDto;
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
	
	@Autowired
	UserDao userDao;
	
	
	
	@PostMapping("/create")
	public ApiResponse<Comment> postComment(@RequestBody CommentDto cmmnt){
		CommentListDto comment = commentService.createNewComment(cmmnt);
		return new ApiResponse<>( 200, Constants.OK, comment);
		
		
	}
	@GetMapping("/{postId}")
	public ApiResponse<List<Comment>> getPostComment(@PathVariable long postId){
		Post post = postDao.findByPostId(postId);
		if(post == null) throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
		List<Comment> allComments = commentDao.findByPostAndReCommentId(post,0);
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
	@PostMapping("/recomment")
	public ApiResponse<Comment> createReComment(@RequestBody ReCommentDto reCommentDto){
		CommentListDto comment = commentService.createNewReComment(reCommentDto);
		return new ApiResponse<Comment>(200,Constants.OK,comment);
		
	}

	@DeleteMapping("/recomment/{commentId}")
	public ApiResponse<String> removeReComment(@PathVariable long commentId){
		Comment comment = commentDao.findByCommentId(commentId);
		if(comment == null) throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
		commentDao.deleteById(commentId);
		return new ApiResponse<>(200, Constants.OK, Constants.DELETED);
	}
	
	@PutMapping("/recomment/{commentId}")
	public ApiResponse<Comment> updateReComment(@RequestBody  ReCommentDto reCommentDto,@PathVariable long commentId){		
		Comment comment = commentDao.findByCommentId(commentId);
		if(comment == null) throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
		comment.setComment(reCommentDto.getCommentedText());
		return new ApiResponse<>(200, Constants.OK, commentDao.save(comment));
	}
	
//	@PostMapping("/recomment")
//	public ApiResponse<ReComment> createReComment(@RequestBody ReCommentDto reCommentDto){
//		CommentListDto comment = commentService.createNewReComment(reCommentDto);
//		return new ApiResponse<ReComment>(200,Constants.OK,comment);
//		
//	}
//
//	@DeleteMapping("/recomment/{commentId}")
//	public ApiResponse<String> removeReComment(@PathVariable long commentId){
//		ReComment comment = reCommentDao.findByReCommentId(commentId);
//		if(comment == null) throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
//		reCommentDao.deleteById(commentId);
//		return new ApiResponse<>(200, Constants.OK, Constants.DELETED);
//	}
//	
//	@PutMapping("/recomment/{commentId}")
//	public ApiResponse<Comment> updateReComment(@RequestBody  ReCommentDto reCommentDto,@PathVariable long commentId){		
//		ReComment comment = reCommentDao.findByReCommentId(commentId);
//		if(comment == null) throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
//		comment.setReComment(reCommentDto.getCommentedText());
//		return new ApiResponse<>(200, Constants.OK, reCommentDao.save(comment));
//	}

}
