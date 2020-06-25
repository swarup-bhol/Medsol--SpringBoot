package com.sm.testcontroller;

//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.sm.controller.FollowControler;
//import com.sm.dao.FollowDao;
//import com.sm.dao.UserDao;
//import com.sm.dto.PersonDto;
//import com.sm.dto.SuggetionsDto;
//import com.sm.model.Follower;
//import com.sm.model.User;
//import com.sm.service.FollowService;
//import com.sm.util.ApiResponse;
//
//@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
public class TestFollowController {
//
//	@InjectMocks
//	FollowControler followControler;
//
//	@Mock
//	FollowDao followDao;
//
//	@Mock
//	UserDao userDao;
//
//	@Mock
//	FollowService followService;
//
//	User user = new User();
//	User follow = new User();
//	Follower follower = new Follower();
//	SuggetionsDto suggetionsDto = new SuggetionsDto();
//	SuggetionsDto suggetionsDto1 = new SuggetionsDto();
//	List<SuggetionsDto> suggetionsDtos = new ArrayList<SuggetionsDto>();
//	List<User> users = new ArrayList<User>();
//
//	@Before
//	public void setUp() {
//
//		user.setUserEmail("swarupbhol34@gmail.com");
//		user.setUserPassword("swarup");
//		user.setUserId(1);
//		follow.setUserEmail("swarupbhol33@gmail.com");
//		follow.setUserPassword("swarup");
//		follow.setUserId(2);
//		follower.setFollowedBy(1);
//		follower.setFollowId(1);
//		follower.setFollowing(true);
//		follower.setUserId(2);
//
//		
//		suggetionsDto.setFollowing(true);
//		suggetionsDto.setProfession("gxzc d");
//		suggetionsDto.setInstitute("df dfz");
//		suggetionsDto.setUserId(1);
//		suggetionsDto.setUserName("dfhj gf");
//		suggetionsDto1.setFollowing(false);
//		suggetionsDto1.setProfession("gxzc d");
//		suggetionsDto1.setInstitute("dfhj");
//		suggetionsDtos.add(suggetionsDto);
//		suggetionsDtos.add(suggetionsDto1);
//		users.add(user);
//		users.add(follow);
//
//	}
//
//	@Test
//	public void testExistFllowUser() {
//
//		when(userDao.findByUserId(1)).thenReturn(user);
//		when(userDao.findByUserId(2)).thenReturn(follow);
//		when(followDao.findByUserIdAndFollowedBy(user.getUserId(), follow.getUserId())).thenReturn(follower);
//		when(followDao.save(follower)).thenReturn(follower);
//		ApiResponse<Follower> apiResponse = followControler.follow(1, 2);
//		assertEquals(200, apiResponse.getStatus());
//
//	}
//
//	@Test
//	public void testNotExistFllowUser() {
//
//		when(userDao.findByUserId(1)).thenReturn(user);
//		when(userDao.findByUserId(2)).thenReturn(follow);
//		when(followDao.findByUserIdAndFollowedBy(user.getUserId(), follow.getUserId())).thenReturn(null);
//		when(followService.followUser(1, 2)).thenReturn(follower);
////		when(followDao.save(follower)).thenReturn(follower);
//		ApiResponse<Follower> apiResponse = followControler.follow(1, 2);
//		assertEquals(200, apiResponse.getStatus());
//
//	}
//
//	@Test
//	public void testUserNotFoundFllowUser() {
//
//		when(userDao.findByUserId(1)).thenReturn(user);
//		when(userDao.findByUserId(2)).thenReturn(null);
//		try {
//			followControler.follow(1, 2);
//		} catch (Exception e) {
//			String message = "User Not Found";
//			assertEquals(message, e.getMessage());
//		}
//
//	}
//
//	@Test
//	public void testExistUnFllowUser() {
//
//		when(userDao.findByUserId(1)).thenReturn(user);
//		when(userDao.findByUserId(2)).thenReturn(follow);
//		when(followDao.findByUserIdAndFollowedBy(user.getUserId(), follow.getUserId())).thenReturn(follower);
//		when(followDao.save(follower)).thenReturn(follower);
//		ApiResponse<Follower> apiResponse = followControler.unFollow(1, 2);
//		assertEquals(200, apiResponse.getStatus());
//
//	}
//
//	@Test
//	public void testNotExistUnFllowUser() {
//
//		when(userDao.findByUserId(1)).thenReturn(user);
//		when(userDao.findByUserId(2)).thenReturn(follow);
//		when(followDao.findByUserIdAndFollowedBy(user.getUserId(), follow.getUserId())).thenReturn(null);
//		ApiResponse<Follower> apiResponse = followControler.unFollow(1, 2);
//		assertEquals(200, apiResponse.getStatus());
//
//	}
//
//	@Test
//	public void testUserNotFoundUnFllowUser() {
//
//		when(userDao.findByUserId(1)).thenReturn(user);
//		when(userDao.findByUserId(2)).thenReturn(null);
//		try {
//			followControler.unFollow(1, 2);
//		} catch (Exception e) {
//			String message = "User Not Found";
//			assertEquals(message, e.getMessage());
//		}
//
//	}
//
//	@Test
//	public void testIsFolowing() {
//		when(userDao.findByUserId(1)).thenReturn(user);
//		when(userDao.findByUserId(2)).thenReturn(follow);
//		when(followService.isFollowing(1, 2)).thenReturn(true);
//		ApiResponse<Boolean> response = followControler.isFollowing(1, 2);
//		assertThat(response.getResult());
//
//	}
//
//	@Test
//	public void testNotFolowing() {
//		when(userDao.findByUserId(1)).thenReturn(user);
//		when(userDao.findByUserId(2)).thenReturn(follow);
//		when(followService.isFollowing(1, 2)).thenReturn(false);
//		ApiResponse<Boolean> response = followControler.isFollowing(1, 2);
//		assertThat(response.getResult());
//
//	}
//
//	@Test
//	public void testExcepIsFolowing() {
//		when(userDao.findByUserId(1)).thenReturn(null);
//		when(userDao.findByUserId(2)).thenReturn(null);
//		try {
//			followControler.isFollowing(1, 2);
//		} catch (Exception e) {
//			String message = "User Not Found";
//			assertEquals(message, e.getMessage());
//		}
//
//	}
//
//	@Test
//	public void testGetAllFollowersForNonEmptyList() {
//		when(userDao.findByUserId(1)).thenReturn(user);
//		when(followService.findAllFollowers(1)).thenReturn(suggetionsDtos);
//		ApiResponse<List<SuggetionsDto>> allFollower = followControler.getAllFollower(1);
//		assertEquals(200, allFollower.getStatus());
//	}
//
//	@Test
//	public void testGetAllFollowersForException() {
//		when(userDao.findByUserId(1)).thenReturn(null);
//		try {
//			followControler.getAllFollower(1);
//		} catch (Exception e) {
//			String message = "User Not Found";
//			assertEquals(message, e.getMessage());
//		}
//
//	}
//	@Test
//	public void testGetAllFollowingUserForNonEmptyList() {
//		when(userDao.findByUserId(1)).thenReturn(user);
//		when(followService.findAllFollowing(1)).thenReturn(suggetionsDtos);
//		ApiResponse<List<SuggetionsDto>> allFollower = followControler.getAllFollowingUser(1);
//		assertEquals(200, allFollower.getStatus());
//	}
//	
//	@Test
//	public void testGetAllFollowingUserForException() {
//		when(userDao.findByUserId(1)).thenReturn(null);
//		try {
//			followControler.getAllFollowingUser(1);
//		} catch (Exception e) {
//			String message = "User Not Found";
//			assertEquals(message, e.getMessage());
//		}
//
//	}
//	
//	@Test
//	public void getAllUnfollowUser() {
//		when(userDao.findByUserId(1)).thenReturn(user);
//		when(followService.getAllUsersNotFollowedByCurrentUser(user,0,6)).thenReturn(suggetionsDtos);
//		ApiResponse<List<PersonDto>> allUnfollowUser = followControler.getAllUnfollowUser(1,0,6);
//		assertEquals(200, allUnfollowUser.getStatus());
//	}
//	@Test
//	public void testgetAllUnfollowUserForException() {
//		when(userDao.findByUserId(1)).thenReturn(null);
//		try {
//			followControler.getAllUnfollowUser(1,0,6);
//		} catch (Exception e) {
//			String message = "User Not Found";
//			assertEquals(message, e.getMessage());
//		}
//
//	}
//	
	

}
