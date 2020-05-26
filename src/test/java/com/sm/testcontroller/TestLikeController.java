
package com.sm.testcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.sm.controller.LikeController;
import com.sm.dao.LikeDao;
import com.sm.dao.PostDao;
import com.sm.dao.UserDao;
import com.sm.exception.PostNotFoundException;
import com.sm.model.Likes;
import com.sm.model.Post;
import com.sm.model.User;
import com.sm.service.LikeService;
import com.sm.util.ApiResponse;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TestLikeController {
	
	@InjectMocks
	LikeController likeController;
	
	@Mock
	LikeDao likeDao;
	
	@Mock
	PostDao postDao;
	
	@Mock
	LikeService likeService;
	
	@Mock
	UserDao userDao;
	
	Post post = new Post();
	User user = new User();
	Likes like = new Likes();
	
	@Before
	public void setUp() {
		post.setPostId(1);
		post.setPostContent("safd sefd");
		user.setUserId(1);
		user.setFullName("Swarup Bhol");
		user.setUserEmail("asdfg@sdf.asd");
	}
	
	@Test
	public void testCreateLikeForPostException() {
		when(postDao.findByPostId(1)).thenReturn(null);
		try {
			likeController.createLike(1, 1);
		}catch (Exception e) {
			String message = "Resource not found";
			assertEquals(message, e.getMessage());
		}
		
	}
	@Test
	public void testCreateLikeForUserException() {
		when(postDao.findByPostId(1)).thenReturn(post);
		when(userDao.findByUserId(1)).thenReturn(null);
		try {
			likeController.createLike(1, 1);
		}catch (Exception e) {
			String message = "User Not Found";
			assertEquals(message, e.getMessage());
		}	
	}
	@Test
	public void testCreateLike() throws PostNotFoundException {
		when(postDao.findByPostId(1)).thenReturn(post);
		when(userDao.findByUserId(1)).thenReturn(user);
		when(likeService.createNewLike(user, post)).thenReturn(like);
			ApiResponse<Likes> createLike = likeController.createLike(1, 1);
			assertEquals(like, createLike.getResult());
	}
	@Test
	public void testCreateUnLikeForPostException() {
		when(postDao.findByPostId(1)).thenReturn(null);
		try {
			likeController.unlikePost(1, 1);
		}catch (Exception e) {
			String message = "Resource not found";
			assertEquals(message, e.getMessage());
		}
		
	}
	@Test
	public void testCreateUnLikeForUserException() {
		when(postDao.findByPostId(1)).thenReturn(post);
		when(userDao.findByUserId(1)).thenReturn(null);
		try {
			likeController.unlikePost(1, 1);
		}catch (Exception e) {
			String message = "User Not Found";
			assertEquals(message, e.getMessage());
		}	
	}
	
	@Test
	public void testUpdateLike() throws PostNotFoundException {
		when(postDao.findByPostId(1)).thenReturn(post);
		when(userDao.findByUserId(1)).thenReturn(user);
		when(likeService.updateUnlike(user, post)).thenReturn(like);
			ApiResponse<Likes> createLike = likeController.unlikePost(1, 1);
			assertEquals(like, createLike.getResult());
	}

}
