package com.sm.testcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import com.sm.controller.PostController;
import com.sm.dao.PostDao;
import com.sm.dao.UserDao;
import com.sm.dto.PostDto;
import com.sm.model.Post;
import com.sm.model.User;
import com.sm.service.PostService;
import com.sm.service.UserService;
import com.sm.util.ApiResponse;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TestPostController { 
	
	@InjectMocks
	PostController postController;
	
	@Mock
	PostDao postDao;
	
	@Mock
	UserDao userDao;
	
	@Mock
	PostService postService;
	
	@Mock
	UserService userService;
	
	MockMultipartFile file;
	User user = new User();
	Post post = new Post();
	PostDto postDto = new PostDto();
	List<PostDto> posts = new ArrayList<PostDto>();
	
	@Before
	public void setUp() {
		file = new MockMultipartFile("file", "filename.png", "img/png", "some img".getBytes());
		user.setUserId(1);
		user.setUserEmail("asdf@asd.asd");
		post.setPostId(1);
		post.setPostContent("sdfsdf");
		post.setPostImgPath("sad ad");
		postDto.setCommentCount(2);
		postDto.setFullName("Sarat Power");
		postDto.setInstituteName("asdfgh sdfgh");
		postDto.setLike(true);
		postDto.setPost(post);
		postDto.setUserId(1);
		posts.add(postDto);
	}
	
	@Test
	public void testUploadPostForNullContent() throws IOException {
		MockMultipartFile file1 = null;
	   ApiResponse<Post> uploadPost = postController.uploadPost((long) 1, "", file1);
	   assertEquals(400, uploadPost.getStatus());		
	}
	
	@Test
	public void testUploadPostForNullUser() throws IOException {
		MockMultipartFile file1 = null;
		when(userService.findByuserId(1)).thenReturn(null);
	  try{
		  postController.uploadPost((long) 1, "sdfsdf", file1);
	  }catch (Exception e) {
		  String message = "User Not Found";
		  assertEquals(message, e.getMessage());
	}
	}
	
	@Test
	public void testUploadPostForNullFile() throws IOException {
		MockMultipartFile file1 = null;
		when(userService.findByuserId(1)).thenReturn(user);
		when(postService.createPost(user, "sdfsdf")).thenReturn(post);
		  ApiResponse<Post> uploadPost = postController.uploadPost((long) 1, "sdfsdf", file1);
		  assertEquals(post, uploadPost.getResult());
	}
	@Test
	public void testUploadPostForFile() throws IOException {
		when(userService.findByuserId(1)).thenReturn(user);
		when(postService.uploadMedia(file, user, "sdfsdf")).thenReturn(post);
		  ApiResponse<Post> uploadPost = postController.uploadPost((long) 1, "sdfsdf", file);
		  assertEquals(post, uploadPost.getResult());
	}
	
	@Test
	public void testUpdatePostForNullContent() throws IOException {
		MockMultipartFile file1 = null;
	   ApiResponse<Post> uploadPost = postController.updateUploadedPost((long) 1, "", file1);
	   assertEquals(400, uploadPost.getStatus());		
	}
	
	@Test
	public void testUpdatePostForNullUser() throws IOException {
		MockMultipartFile file1 = null;
		when(postService.getPostById(1)).thenReturn(null);
	  
		  ApiResponse<Post> updateUploadedPost = postController.updateUploadedPost((long) 1, "sdfsdf", file1);
		  assertEquals(204, updateUploadedPost.getStatus());
	}
	
	@Test
	public void testUpdatePostForNullFile() throws IOException {
		MockMultipartFile file1 = null;
		when(postService.getPostById(1)).thenReturn(post);
		when(postDao.save(any(Post.class))).thenReturn(post);
	  
		  ApiResponse<Post> updateUploadedPost = postController.updateUploadedPost((long) 1, "sdfsdf", file1);
		  assertEquals(post, updateUploadedPost.getResult());
	}
	@Test
	public void testUpdatePostForFile() throws IOException {
		when(postService.getPostById(1)).thenReturn(post);
		when(postService.updateMedia(post, "sdfsdf", file)).thenReturn(post);
		  ApiResponse<Post> updateUploadedPost = postController.updateUploadedPost((long) 1, "sdfsdf", file);
		  assertEquals(post, updateUploadedPost.getResult());
	}
	
	@Test
	public void testDeletePostForInvalidId() {
		when(postDao.findByPostId(1)).thenReturn(null);		
		try {
			postController.deletePost(1);
		}catch (Exception e) {
		     String message = "Resource not found";
		     assertEquals(message, e.getMessage());
		}
		
	}
	
	@Test
	public void testGetAllPostByUserForNullUser() {
		when(userService.findByuserId(1)).thenReturn(null);
		try {
			postController.getAllPostByUser(1, 0);
		}catch (Exception e) {
			String message = "User Not Found";
		     assertEquals(message, e.getMessage());
		}
	}
	@Test
	public void testGetAllPostByUser() {
		when(userService.findByuserId(1)).thenReturn(user);
		when(postService.getUploadedPost(user, 0)).thenReturn(posts);	
		ApiResponse<List<PostDto>> allPostByUser = postController.getAllPostByUser(1, 0);
		assertEquals(posts, allPostByUser.getResult());
	}
	
	@Test
	public void testGetAllPostForNullUser() {
		when(userDao.findByUserId(1)).thenReturn(null);
		try {
			postController.getAllPost(1, 0);
		}catch (Exception e) {
			String message = "User Not Found";
		     assertEquals(message, e.getMessage());
		}
		
	}
	@Test
	public void testGetAllPost() {
		when(userDao.findByUserId(1)).thenReturn(user);
		when(postService.getNewsFeedPosts(user, 0)).thenReturn(posts);	
		ApiResponse<List<PostDto>> allPostByUser = postController.getAllPost(1, 0);
		assertEquals(posts, allPostByUser.getResult());
	}
	

}
