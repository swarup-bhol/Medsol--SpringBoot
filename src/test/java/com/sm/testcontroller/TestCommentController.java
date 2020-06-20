package com.sm.testcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.sm.controller.CommentController;
import com.sm.dao.CommentDao;
import com.sm.dao.PostDao;
import com.sm.dto.CommentDto;
import com.sm.dto.CommentListDto;
import com.sm.model.Comment;
import com.sm.model.Post;
import com.sm.service.CommentService;
import com.sm.util.ApiResponse;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TestCommentController {

	@InjectMocks
	CommentController commentController;
	
	@Mock
	PostDao postDao;
	
	@Mock
	CommentDao commentDao;
	
	@Mock
	CommentService commentService;
	
	Post post = new Post();
	Comment comment = new Comment();
	CommentDto commentDto =new CommentDto();
	CommentListDto commentLists =new CommentListDto();
	List<Comment> comments = new ArrayList<Comment>();
	
	@Before
	public void setUp() {
		commentDto.setMessage("sdfdgf");
		commentDto.setPostId(1);
		commentDto.setUserId(1);
		commentLists.setCommentedText(commentDto.getMessage());
		commentLists.setCommentId(1);
		commentLists.setUserId(1);
		commentLists.setUserName("Swarup Bhol");
		post.setPostContent(commentDto.getMessage());
		post.setPostId(1);
		post.setPostImgPath("asdfasdfgh");
		post.setRecordStatus(true);
		comment.setComment("sada asd");
		comment.setCommentId(1);
		comment.setPost(post);
		comments.add(comment);
	}
	
	@Test
	public void testPostComment() {
		when(commentService.createNewComment(commentDto)).thenReturn(commentLists);
		ApiResponse<Comment> postComment = commentController.postComment(commentDto);
		assertEquals(200, postComment.getStatus());
	}
	
	@Test
	public void testGetPostCommentforException() {
		when(postDao.findByPostId(1)).thenReturn(null);
		try {
			commentController.getPostComment(1);
		}catch (Exception e) {
			String message = "Resource not found";
			assertEquals(message, e.getMessage());
		}
		
	}
	@Test
	public void testGetPostComment() {
		when(postDao.findByPostId(1)).thenReturn(post);
		when(commentDao.findByPostAndReCommentId(post,0)).thenReturn(comments);
		ApiResponse<List<Comment>> postComment = commentController.getPostComment(1);
		assertEquals(comments, postComment.getResult());	
	}
	
	@Test
	public void testremoveComment() {
		when(commentDao.findByCommentId(1)).thenReturn(comment);
		ApiResponse<String> postComment = commentController.removeComment(1);
		assertEquals(200, postComment.getStatus());
		
	}
	@Test
	public void testremoveCommentforException() {
		when(commentDao.findByCommentId(1)).thenReturn(null);
		try {
			commentController.removeComment(1);
		}catch (Exception e) {
			String message = "Resource not found";
			assertEquals(message, e.getMessage());
		}
		
	}
}
