package com.sm.testservice;

//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.mock.web.MockMultipartFile;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.sm.dao.CommentDao;
//import com.sm.dao.LikeDao;
//import com.sm.dao.PostDao;
//import com.sm.dao.ProfessionDao;
//import com.sm.dto.PostDto;
//import com.sm.model.Comment;
//import com.sm.model.Likes;
//import com.sm.model.Post;
//import com.sm.model.Profession;
//import com.sm.model.User;
//import com.sm.service.impl.PostServiceImpl;
//
//@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
public class TestPostService {
//	
//	@InjectMocks
//	PostServiceImpl postService;
//	@Mock
//	PostServiceImpl postSev;
//	
//	@Mock
//	PostDao postDao;
//	
//	@Mock
//	CommentDao commentDao;
//	
//	@Mock
//	LikeDao likeDao;
//	
//	@Mock
//	ProfessionDao profDao;
//	
//	
//	User user = new User();
//	Post post = new Post();
//	Likes like = new Likes();
//	Comment comment = new Comment();
//	Profession profession  = new Profession();
//	List<Comment> comments = new ArrayList<Comment>();
//	List<Post> posts = new ArrayList<Post>();
//	
//	@Before
//	public void setUp() {
//		user.setUserId(1);
//		user.setUserEmail("swarup@as.com");
//		user.setFullName("Swarup Bhol");
//		user.setProfessionId(1);
//		post.setPostContent("sdf df");
//		post.setPostId(1);
//		post.setPostImgPath("srdjghk sdfhg");
//		post.setUser(user);
//		post.setRecordStatus(true);
//		posts.add(post);
//		
//		comment.setCommentId(1);
//		comment.setComment("sdfs sdf");
//		comment.setPost(post);
//		comment.setUser(user);
//		comments.add(comment);
//		
//		like.setLikeId(1);
//		like.setPost(post);
//		like.setUser(user);
//		like.setRecordStatus(true);
//	}
//
//	@Test
//	public void testUploadMedia() throws IOException {
//		MockMultipartFile file = new MockMultipartFile("file", "filename.png", "img/png", "some img".getBytes());
//		when(postDao.save(any(Post.class))).thenReturn(post);
//		Post uploadMedia = postService.uploadMedia(file, user, "sdf df");
//		assertEquals(post, uploadMedia);		
//	}
//	
//	@Test
//	public void testCreatePost() {
//		when(postDao.save(any(Post.class))).thenReturn(post);		
//		Post createPost = postService.createPost(user, "sdf df");
//		assertEquals(post, createPost);	
//	}
//	
//	@Test
//	public void  testPostById() {
//		when(postDao.findByPostId(1)).thenReturn(post);		
//		Post postById = postService.getPostById(1);
//		assertEquals(post, postById);
//	}
//	
//
//	@Test
//	public void testUpdateMedia() throws IOException {
//		MockMultipartFile file = new MockMultipartFile("file", "filename.png", "img/png", "some img".getBytes());
//		when(postDao.save(any(Post.class))).thenReturn(post);
//		Post uploadMedia = postService.updateMedia(post, "sdf df", file);
//		assertEquals(post, uploadMedia);	
//	}
//	
//	@Test
//	public void getNewsFeedPost() {
//		when(postDao.findAllByRecordStatusOrderByPostIdDesc(true,PageRequest.of(0, 10))).thenReturn(posts);
//		when(commentDao.findByPostAndReCommentId(post,0)).thenReturn(comments);
//		when(likeDao.countByPost(post)).thenReturn((long) 2);
//		when(likeDao.findByPostAndUserAndCommentId(post, user,0)).thenReturn(like);
//		when(profDao.findByProfessionId(1)).thenReturn(profession);
//		
//		List<PostDto> newsFeedPosts = postService.getNewsFeedPosts(user, 0);
//		
//		assertEquals(1, newsFeedPosts.size());
//		
//	}
//	@Test
//	public void getNewsFeedPostForZeroLike() {
//		when(postDao.findAllByRecordStatusOrderByPostIdDesc(true,PageRequest.of(0, 10))).thenReturn(posts);
//		when(commentDao.findByPostAndReCommentId(post,0)).thenReturn(comments);
//		when(likeDao.countByPost(post)).thenReturn((long) 2);
//		when(likeDao.findByPostAndUserAndCommentId(post, user,0)).thenReturn(null);
//		when(profDao.findByProfessionId(1)).thenReturn(profession);
//		
//		List<PostDto> newsFeedPosts = postService.getNewsFeedPosts(user, 0);
//		
//		assertEquals(1, newsFeedPosts.size());
//	}
//	
//	@Test
//	public void getNewsFeedPostForEmptyList() {
//		when(postDao.findAllByRecordStatusOrderByPostIdDesc(true,PageRequest.of(0, 10))).thenReturn(new ArrayList<>());
//		List<PostDto> uploadedPost = postService.getNewsFeedPosts(user, 0);
//		assertEquals(0, uploadedPost.size());
//		
//	}
//	
//	@Test
//	public void getUploadedPostForEmptyList() {
//		when(postDao.findAllByUserAndRecordStatusOrderByPostIdDesc(user,true, PageRequest.of(0, 10))).thenReturn(new ArrayList<>());
//		List<PostDto> uploadedPost = postService.getUploadedPost(user, 0);
//		assertEquals(0, uploadedPost.size());
//		
//	}
//
//	@Test
//	public void getUploadedPostForNonEmptyList() {
//		when(postDao.findAllByUserAndRecordStatusOrderByPostIdDesc(user,true, PageRequest.of(0, 10))).thenReturn(posts);
//		when(commentDao.findByPostAndReCommentId(post,0)).thenReturn(comments);
//		when(likeDao.countByPost(post)).thenReturn((long) 2);
//		when(likeDao.findByPostAndUserAndCommentId(post, user,0)).thenReturn(null);
//		when(profDao.findByProfessionId(1)).thenReturn(profession);
//		List<PostDto> uploadedPost = postService.getUploadedPost(user, 0);
//		assertEquals(1, uploadedPost.size());
//		
//	}
//	
}
