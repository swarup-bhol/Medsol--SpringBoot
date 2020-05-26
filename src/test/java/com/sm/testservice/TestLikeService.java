package com.sm.testservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.sm.dao.LikeDao;
import com.sm.model.Likes;
import com.sm.model.Post;
import com.sm.model.User;
import com.sm.service.impl.LikeServiceImpl;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TestLikeService {
	
	@Mock
	LikeDao likeDao;
	
	@InjectMocks
	LikeServiceImpl likeService;
	
	User user  = new User();
	Post post = new Post();
	Likes like = new Likes();

	@Before
	public void setUp() {
		user.setUserId(1);
		user.setFullName("dfhjk");
		user.setUserEmail("sdgfd@dfd.fga");
		post.setPostId(1);
		post.setPostImgPath("gfkjl");
		post.setUser(user);
		like.setLikeId(1);
		like.setPost(post);
		like.setUser(user);
		like.setRecordStatus(true);
		
	}
	@Test
	public void testCreateNewLikeForExisting() {
		when(likeDao.findByPostAndUser(post, user)).thenReturn(like);	
		when(likeDao.save(like)).thenReturn(like);
		Likes newLike = likeService.createNewLike(user, post);
		assertEquals(like, newLike);
		
		
	}
	@Test
	public void testCreateNewLike() {
		when(likeDao.findByPostAndUser(post, user)).thenReturn(null);	
		when(likeDao.save(any(Likes.class))).thenReturn(like);
		Likes newLike = likeService.createNewLike(user, post);
		assertEquals(like, newLike);
		
		
	}
	
	@Test
	public void testUpdateUnlike() {
		when(likeDao.findByPostAndUser(post, user)).thenReturn(like);
		when(likeDao.save(like)).thenReturn(like);
		
		Likes newLike = likeService.updateUnlike(user, post);
		assertEquals(like, newLike);
		
	}
	@Test
	public void testUpdateUnlikeForNullValue() {
		when(likeDao.findByPostAndUser(post, user)).thenReturn(null);		
		Likes newLike = likeService.updateUnlike(user, post);
		assertEquals(null, newLike);
		
	}
}
