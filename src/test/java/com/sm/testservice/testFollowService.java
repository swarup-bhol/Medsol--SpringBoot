package com.sm.testservice;

//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageRequest;
//
//import com.sm.dao.FollowDao;
//import com.sm.dao.ProfessionDao;
//import com.sm.dao.UserDao;
//import com.sm.dto.SuggetionsDto;
//import com.sm.model.Follower;
//import com.sm.model.Profession;
//import com.sm.model.User;
//import com.sm.service.impl.FollowServiceImpl;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
public class TestFollowService {
	
//	@InjectMocks
//	FollowServiceImpl followService;
//	
//	@Mock
//	FollowDao followDao;
//	
//	@Mock
//	UserDao userDao;
//	
//	@Mock
//	ProfessionDao professionDao;
//	
//	User user = new User();
//	Follower follower = new Follower();
//	Profession profession = new Profession();
//	List<Follower> followers = new ArrayList<Follower>();
//	List<User> users = new ArrayList<User>();
//	List<Long> userList =  new ArrayList<Long>();
//	@Before
//	public void setup() {
//		follower.setFollowId(1);
//		follower.setFollowedBy(2);
//		follower.setFollowing(true);
//		follower.setFollowId(1);
//		follower.setUserId(1);
//		follower.setFollowId(2);
//		followers.add(follower);
//		user.setUserId(1);
//		user.setUserEmail("sdfhj@fdgh.dfg");
//		user.setFullName("sdhg");
//		user.setProfessionId(1);
////		profession.setId(1);
////		profession.setName("dfghjk");
//		users.add(user);
//		userList.add((long) 2);
//		
//	}
//	
//	@Test
//	public void testFollowUser() {
//		when(followDao.save(any(Follower.class))).thenReturn(follower);
//		
//		Follower followUser = followService.followUser(1, 2);
//		assertEquals(follower, followUser);
//	}
//	
//	@Test
//	public void testIsFollowingForTrue() {
//		when(followDao.findByUserIdAndFollowedBy(1, 2)).thenReturn(follower);
//		boolean isFollowing = followService.isFollowing(2, 1);
//		assertThat(isFollowing);
//		
//	}
//	@Test
//	public void testIsFollowingForFalse() {
//		follower.setFollowing(false);
//		when(followDao.findByUserIdAndFollowedBy(1, 2)).thenReturn(follower);
//		boolean isFollowing = followService.isFollowing(2, 1);
//		assertThat(isFollowing);
//		
//	}
//	@Test
//	public void testFindAllFollowing() {
//		when(followDao.findByFollowedByAndIsFollowing(1, true)).thenReturn(followers);
//		when(userDao.findByUserId(1)).thenReturn(user);
//		when(professionDao.findByProfessionId(1)).thenReturn(profession);	
//		List<SuggetionsDto> findAllFollowing = followService.findAllFollowing(1);
//		assertEquals(1, findAllFollowing.size());
//		
//	}
//	@Test
//	public void testFindAllFollowingForEmptyFollower() {
//		when(followDao.findByFollowedByAndIsFollowing(1, true)).thenReturn(null);
//		List<SuggetionsDto> findAllFollowing = followService.findAllFollowing(1);
//		assertEquals(0, findAllFollowing.size());
//		
//	}
//	
//	@Test
//	public void testFindAllFollowingForNullUser() {
//		when(followDao.findByFollowedByAndIsFollowing(1, true)).thenReturn(followers);
//		when(userDao.findByUserId(1)).thenReturn(null);
//		try{
//			followService.findAllFollowing(1);
//		}catch (Exception e) {
//			String message ="User Not Found";
//			assertEquals(message, e.getMessage());
//		}		
//	}
//	@Test
//	public void testFindAllFollowingForNullProff() {
//		when(followDao.findByFollowedByAndIsFollowing(1, true)).thenReturn(followers);
//		when(userDao.findByUserId(1)).thenReturn(user);
//		when(professionDao.findByProfessionId(1)).thenReturn(null);	
//		try{
//			followService.findAllFollowing(1);
//		}catch (Exception e) {
//			String message ="Resource not found";
//			assertEquals(message, e.getMessage());
//		}		
//	}
//	
//	@Test
//	public void testFindAllFollower() {
//		when(followDao.findByUserIdAndIsFollowing(1, true)).thenReturn(followers);
//		when(userDao.findByUserId(2)).thenReturn(user);
//		when(professionDao.findByProfessionId(1)).thenReturn(profession);	
//		List<SuggetionsDto> findAllFollowing = followService.findAllFollowers(1);
//		assertEquals(1, findAllFollowing.size());
//		
//	}
//	@Test
//	public void testFindAllFollowerForEmptyFollower() {
//		when(followDao.findByUserIdAndIsFollowing(1, true)).thenReturn(null);
//		List<SuggetionsDto> findAllFollowing = followService.findAllFollowers(1);
//		assertEquals(0, findAllFollowing.size());
//		
//	}
//	
//	@Test
//	public void testFindAllFollowerForNullUser() {
//		when(followDao.findByUserIdAndIsFollowing(1, true)).thenReturn(followers);
//		when(userDao.findByUserId(2)).thenReturn(null);
//		try{
//			followService.findAllFollowers(1);
//		}catch (Exception e) {
//			String message ="User Not Found";
//			assertEquals(message, e.getMessage());
//		}		
//	}
//	@Test
//	public void testFindAllFollowerForNullProff() {
//		when(followDao.findByUserIdAndIsFollowing(1, true)).thenReturn(followers);
//		when(userDao.findByUserId(2)).thenReturn(user);
//		when(professionDao.findByProfessionId(1)).thenReturn(null);	
//		try{
//			followService.findAllFollowers(1);
//		}catch (Exception e) {
//			String message ="Resource not found";
//			assertEquals(message, e.getMessage());
//		}		
//	}
//	
//	@Test
//	public void testAllUserNotFollowedByUser() {
//		when(followDao.findAllFolowerId(1, true)).thenReturn(userList);
//		when(userDao.findAllUser(userList, PageRequest.of(0,10))).thenReturn(users);	
//		List<SuggetionsDto> list = followService.getAllUsersNotFollowedByCurrentUser(user, 0, 10);
//		assertEquals(users.size(), list.size());
//		
//		
//	}
}
