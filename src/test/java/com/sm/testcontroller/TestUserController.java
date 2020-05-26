package com.sm.testcontroller;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.AuthenticationException;

import com.sm.controller.UserController;
import com.sm.dao.UserDao;
import com.sm.dto.JwtToken;
import com.sm.dto.LoginUser;
import com.sm.dto.ProfileDetailsDto;
import com.sm.dto.UserDto;
import com.sm.exception.InvalidUserNamePasswordException;
import com.sm.model.User;
import com.sm.service.UserService;
import com.sm.util.ApiResponse;
import com.sm.util.JwtTokenUtil;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TestUserController {

	@Mock
	UserService userService;

	@Mock
	JwtTokenUtil jwtTokenUtil;

	@Mock
	UserDao userDao;
	
//	@InjectMocks
//	UserController userController;
//
//	LoginUser loginUser = new LoginUser();
//	MockMultipartFile file;
//	User user = new User();
//	List<User> users = new ArrayList<User>();
//	ProfileDetailsDto profileDetailsDto = new ProfileDetailsDto();
//	UserDto userDto = new UserDto();
//	ProfileInfo profile = new ProfileInfo();
//	String fileName = "image.jpg";
// 
//	@Before
//	public void setUp() {
//		
//		loginUser.setEmail("swarupbhol34@gmail.com");
//		loginUser.setPassword("swarup");
//		user.setUserEmail("swarupbhol34@gmail.com");
//		user.setUserPassword("swarup");
//		user.setUserId(1);
//		userDto.setEmail("swarupbhol34@gmail.com");
//		userDto.setMobile("1234567890");
//		userDto.setName("Swarup Bhol");
//		userDto.setPassword("asdfghjkl");
//		profile.setGrade("gfhkl");
//		profile.setInstitute("dsfhjk");
//		profile.setProfession(1);
//		profile.setSpecialty(2);
//		profile.setSubspecialties(1);
//		profileDetailsDto.setDetailsSpecialization("sdc sdf");
//		profileDetailsDto.setFollowerCount(1);
//		profileDetailsDto.setFollowingCount(1);
//		profileDetailsDto.setProfession("sd asd");
//		profileDetailsDto.setSpecialization("asd sda");
//		profileDetailsDto.setUser(user);
//		file = new MockMultipartFile("file", "filename.png", "img/png", "some img".getBytes());
//		users.add(user);
//	}
//	
//	
//	@Test
//	public void testExistingRegisteruser() {
//
//		when(userService.checkUser(userDto)).thenReturn(true);
//		ApiResponse<User> responseEntity = userController.saveUser(userDto);
//		assertEquals(409, responseEntity.getStatus());
//
//	}
//	
//	@Test
//	public void testNewRegisteruser() {
//
//		when(userService.checkUser(userDto)).thenReturn(false);
//		when(userService.save(userDto)).thenReturn(user);
//		ApiResponse<User> responseEntity = userController.saveUser(userDto);
//		assertEquals(user, responseEntity.getResult());
//
//	}
//	
//	
//	@Test
//	public void testUserLoginWithNullValue() throws AuthenticationException, InvalidUserNamePasswordException{
//        LoginUser login = null;
//		ApiResponse<JwtToken> responseEntity = userController.loginUser(login);
//		assertEquals(401, responseEntity.getStatus());
//
//	} 
//
//	@Test
//	public void testUserLoginWithCorrectCredentials() throws AuthenticationException, InvalidUserNamePasswordException{
//
//		when(userService.loginUser(loginUser)).thenReturn(true);
//		when(userService.findOne("swarupbhol34@gmail.com")).thenReturn(user);
//		when(jwtTokenUtil.generateToken(user)).thenReturn("sdfghjklsdfghjkdfg");
//		ApiResponse<JwtToken> responseEntity = userController.loginUser(loginUser);
//		assertEquals(200, responseEntity.getStatus());
//
//	} 
//	
//	@Test
//	public void testUserLoginWithInCorrectCredentials() throws AuthenticationException, InvalidUserNamePasswordException{
//
//		when(userService.loginUser(loginUser)).thenReturn(false);
//		ApiResponse<JwtToken> responseEntity = userController.loginUser(loginUser);
//		assertEquals(400, responseEntity.getStatus());
//
//	}
//	
//	@Test
//	public void createProfile(){
//
//		when(userDao.findByUserEmail(user.getUserEmail())).thenReturn(user);
//		when(userService.createProfile(profile, user)).thenReturn(user);
//		when(jwtTokenUtil.generateToken(user)).thenReturn("sdfghjklsdfghjkdfg");
//		ApiResponse<JwtToken> responseEntity = userController.createProfile(profile, user.getUserEmail());
//		assertEquals(200, responseEntity.getStatus());
//
//	}
//	
//	@Test
//	public void createProfileIfExist(){
//
//		when(userDao.findByUserEmail(user.getUserEmail())).thenReturn(null);
//		try {
//		    userController.createProfile(profile, user.getUserEmail());
//		} 
//		catch (Exception e) {
//		    String expectedMessage = "User Not Found";
//		    Assert.assertEquals(expectedMessage, e.getMessage() );
//		} 
//
//	}
//	
//	@Test
//	public void testUploadPostFOrNullUser() throws IOException {
//		when(userService.findByuserId(1)).thenReturn(null);
//		try {
//		    userController.uploadProfilePic(file, 1);
//		} 
//		catch (Exception e) {
//		    String expectedMessage = "User Not Found";
//		    Assert.assertEquals(expectedMessage, e.getMessage() );
//		} 
//	}
//	@Test
//	public void testUploadPost() throws IOException {
//		when(userService.findByuserId(1)).thenReturn(user);
//		when(userService.uploadProfilePic(file, user)).thenReturn(user);
//		    ApiResponse<User> uploadProfilePic = userController.uploadProfilePic(file, 1);
//		    assertEquals(user, uploadProfilePic.getResult());
//		
//	}
//	
//	@Test
//	public void testGetProfileSetailsForNulluser() {
//		when(userDao.findByUserId(1)).thenReturn(null);
//		try {
//		    userController.getprofileDetails(1);
//		} 
//		catch (Exception e) {
//		    String expectedMessage = "User Not Found";
//		    Assert.assertEquals(expectedMessage, e.getMessage() );
//		}
//		
//	}
//	@Test
//	public void testGetProfileSetails() {
//		when(userDao.findByUserId(1)).thenReturn(user);
//		when(userService.getProfileDetails(user)).thenReturn(profileDetailsDto);
//		    ApiResponse<ProfileDetailsDto> getprofileDetails = userController.getprofileDetails(1);
//		    assertEquals(profileDetailsDto, getprofileDetails.getResult());
//		
//	}
//	
//	@Test
//	public void testSearchUser() {
//		when(userDao.findUsersWithStartPartOfName("s")).thenReturn(users);
//		ApiResponse<List<User>> searchUser = userController.searchUser("s");
//		assertEquals(users, searchUser.getResult());	
//	}
//	@Test
//	public void testSearchUserForNotFoundWIthStartame() {
//		List<User> userList = new ArrayList<User>();
//		when(userDao.findUsersWithStartPartOfName("s")).thenReturn(userList);
//		when(userDao.findUsersWithPartOfName("s")).thenReturn(users);
//		ApiResponse<List<User>> searchUser = userController.searchUser("s");
//		assertEquals(users, searchUser.getResult());	
//	}

}
