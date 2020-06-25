package com.sm.testservice;

//import static org.mockito.ArgumentMatchers.any;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//import java.sql.Timestamp;
//import java.time.Instant;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.sm.dao.CommentDao;
//import com.sm.dao.PostDao;
//import com.sm.dto.CommentDto;
//import com.sm.dto.CommentListDto;
//import com.sm.model.Comment;
//import com.sm.model.Post;
//import com.sm.model.User;
//import com.sm.service.UserService;
//import com.sm.service.impl.CommentServiceImpl;
//
//@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
public class TestCommentService {
	
//	@InjectMocks
//	CommentServiceImpl commentService;	
//	
//	
//	@Mock
//	UserService userService;
//	
//	@Mock
//	PostDao postDao;
//	
//	@Mock
//	CommentDao  commentDao;
//	
//	CommentDto commnetDto =new CommentDto();	
//	Post post = new Post();
//	User user = new User();
//	Comment comnt = new Comment();
//	CommentListDto listDtos = new CommentListDto();
//	
//	@Before
//	public void setup() {
//		Instant instant = Instant.now();
//		Timestamp timestamp = Timestamp.from(instant);
//		commnetDto.setMessage("Hi good Morning All");
//		commnetDto.setPostId(1);
//		commnetDto.setUserId(1);
//		post.setPostContent("gdjmfv");
//		post.setPostId(1);
//		post.setPostImgPath("fdghkj");
//		post.setRecordStatus(true);
//		
//		user.setUserId(1);
//		user.setUserEmail("fkjl@gfj.gfh");
//		user.setUserMobile("1234567890");
//		user.setInstituteName("dhgj gfhj");
//		user.setFullName("shgj");
//		
//		comnt.setComment("dfghjkl");
//		comnt.setCommentId(1);
//		comnt.setUser(user);
//		comnt.setPost(post);
//		comnt.setCreatedTime(timestamp);
//		
//		listDtos.setCommentedText(comnt.getComment());
//		listDtos.setCommentId(comnt.getCommentId());
//		listDtos.setUserId(1);
//		listDtos.setCommentedTime(timestamp);
//		listDtos.setUserName(user.getFullName());
//		
//		
//	}
//	
//	
//	@Test
//	public void testNewCommentForPostException() {
//		
//		when(postDao.findByPostId(1)).thenReturn(null);
//		
//		try {
//			commentService.createNewComment(commnetDto);
//			
//		}catch (Exception e) {
//			String message = "Post Not found";
//			assertEquals(message, e.getMessage());
//		}
//		
//	}
//	@Test
//	public void testNewCommentForUserException() {
//		when(postDao.findByPostId(1)).thenReturn(post);
//		when(userService.findByuserId(1)).thenReturn(null);
//		
//		try {
//			commentService.createNewComment(commnetDto);
//			
//		}catch (Exception e) {
//			String message = "User Not Found";
//			assertEquals(message, e.getMessage());
//		}
//		
//	}
//	
//	@Test
//	public void testCreateNewComment() {
//		when(postDao.findByPostId(1)).thenReturn(post);
//		when(userService.findByuserId(1)).thenReturn(user);
//		when(commentDao.save(any(Comment.class))).thenReturn(comnt);	
//		CommentListDto createNewComment = commentService.createNewComment(commnetDto);
//		assertThat(listDtos).isEqualToComparingFieldByField(createNewComment);
//	}
// 
//	

}
