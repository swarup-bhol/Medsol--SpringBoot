package com.sm.testcontroller;

//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.when;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.core.AuthenticationException;
//
//import com.sm.controller.UserController;
//import com.sm.dao.UserDao;
//import com.sm.dto.JwtToken;
//import com.sm.dto.LoginUser;
//import com.sm.dto.PasswordDto;
//import com.sm.dto.Profile;
//import com.sm.dto.ProfileDetailsDto;
//import com.sm.dto.SuggetionsDto;
//import com.sm.dto.UpdateProfileDto;
//import com.sm.dto.UserDto;
//import com.sm.exception.InvalidUserNamePasswordException;
//import com.sm.model.User;
//import com.sm.service.UserService;
//import com.sm.util.ApiResponse;
//import com.sm.util.JwtTokenUtil;
//
//@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
public class TestUserController {

//	@Mock
//	UserService userService;
//
//	@Mock
//	JwtTokenUtil jwtTokenUtil;
//
//	@Mock
//	UserDao userDao;
//
//	@InjectMocks
//	UserController userController;
//
//	LoginUser loginUser = new LoginUser();
//	MockMultipartFile file;
//	User user = new User();
//	List<User> users = new ArrayList<User>();
//	List<SuggetionsDto> suggetionsDtos = new ArrayList<SuggetionsDto>();
//	ProfileDetailsDto profileDetailsDto = new ProfileDetailsDto();
//	SuggetionsDto suggetionsDto = new SuggetionsDto();
//	UpdateProfileDto profileDto = new UpdateProfileDto();
//	UserDto userDto = new UserDto();
//	Profile profile = new Profile();
//	String fileName = "image.jpg";
//	PasswordDto passDto = new PasswordDto();
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
//		profile.setGrade(1);
//		profile.setInstitute("dsfhjk");
//		profile.setProfession(1);
//		profile.setSpecialization(2);
//		profile.setSubspecialization(1);
//
//		suggetionsDto.setFollowing(true);
//		suggetionsDto.setInstitute("Hello World");
//		suggetionsDto.setProfession("Hello");
//		suggetionsDto.setUserId(1);
//		suggetionsDto.setUserName("Swarup Bhol");
//		suggetionsDtos.add(suggetionsDto);
//
//		profileDto.setEmail("swarupbhol34Gmail.com");
//		profileDto.setGrade("fda");
//		profileDto.setInstitue("AIIMS BBSR");
//		profileDto.setMobileNo("6543542342");
//		profileDto.setProfession("sdhfasfd");
//		profileDto.setSpecialization("sdaf asdf");
//		profileDto.setSubspecialization("gghsda hgsda");
//
//		passDto.setCnfPassword("swarup");
//		passDto.setOldPassword("swarup");
//		passDto.setPassword("satya");
//
//		file = new MockMultipartFile("file", "filename.png", "img/png", "some img".getBytes());
//		users.add(user);
//	}
//
//	@Test
//	public void testExistingRegisteruser() {
//		when(userService.checkUser(userDto)).thenReturn(true);
//		ApiResponse<User> responseEntity = userController.saveUser(userDto);
//		assertEquals(409, responseEntity.getStatus());
//	}
//
//	@Test
//	public void testNewRegisteruser() {
//		when(userService.checkUser(userDto)).thenReturn(false);
//		when(userService.save(userDto)).thenReturn(user);
//		ApiResponse<User> responseEntity = userController.saveUser(userDto);
//		assertEquals(user, responseEntity.getResult());
//	}
//
//	@Test
//	public void testUserLoginWithNullValue() throws AuthenticationException, InvalidUserNamePasswordException {
//		LoginUser login = null;
//		ApiResponse<JwtToken> responseEntity = userController.loginUser(login);
//		assertEquals(401, responseEntity.getStatus());
//
//	}
//
//	@Test
//	public void testUserLoginWithCorrectCredentials() throws AuthenticationException, InvalidUserNamePasswordException {
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
//	public void testUserLoginWithInCorrectCredentials()
//			throws AuthenticationException, InvalidUserNamePasswordException {
//
//		when(userService.loginUser(loginUser)).thenReturn(false);
//		ApiResponse<JwtToken> responseEntity = userController.loginUser(loginUser);
//		assertEquals(400, responseEntity.getStatus());
//
//	}
//
//	@Test
//	public void createProfile() {
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
//	public void createProfileIfExist() {
//
//		when(userDao.findByUserEmail(user.getUserEmail())).thenReturn(null);
//		try {
//			userController.createProfile(profile, user.getUserEmail());
//		} catch (Exception e) {
//			String expectedMessage = "User Not Found";
//			Assert.assertEquals(expectedMessage, e.getMessage());
//		}
//
//	}
//
//	@Test
//	public void testUploadPostFOrNullUser() throws IOException {
//		when(userService.findByuserId(1)).thenReturn(null);
//		try {
//			userController.uploadProfilePic(file, 1);
//		} catch (Exception e) {
//			String expectedMessage = "User Not Found";
//			Assert.assertEquals(expectedMessage, e.getMessage());
//		}
//	}
//
//	@Test
//	public void testUploadPost() throws IOException {
//		when(userService.findByuserId(1)).thenReturn(user);
//		when(userService.uploadProfilePic(file, user)).thenReturn(user);
//		ApiResponse<User> uploadProfilePic = userController.uploadProfilePic(file, 1);
//		assertEquals(user, uploadProfilePic.getResult());
//
//	}
//
//	@Test
//	public void testGetProfileSetailsForNulluser() {
//		when(userDao.findByUserId(1)).thenReturn(null);
//		try {
//			userController.getprofileDetails(1);
//		} catch (Exception e) {
//			String expectedMessage = "User Not Found";
//			Assert.assertEquals(expectedMessage, e.getMessage());
//		}
//
//	}
//
//	@Test
//	public void testGetProfileSetails() {
//		when(userDao.findByUserId(1)).thenReturn(user);
//		when(userService.getProfileDetails(user)).thenReturn(profileDetailsDto);
//		ApiResponse<ProfileDetailsDto> getprofileDetails = userController.getprofileDetails(1);
//		assertEquals(profileDetailsDto, getprofileDetails.getResult());
//	}
//
//	@Test
//	public void updateProfileDetails() {
//		when(userService.updateProfile(1, profileDto)).thenReturn(profileDetailsDto);
//		ApiResponse<ProfileDetailsDto> updateProfileDetails = userController.updateProfileDetails(profileDto, 1);
//		assertEquals(profileDetailsDto, updateProfileDetails.getResult());
//	}
//
//	@Test
//	public void testSearchUser() {
//		when(userService.searchUser("asds", 1)).thenReturn(suggetionsDtos);
//		ApiResponse<List<SuggetionsDto>> searchUser = userController.searchUser(1, "asds");
//		assertEquals(suggetionsDtos, searchUser.getResult());
//	}
//
//	@Test
//	public void testResetPasswordForNullUser() {
//		when(userDao.findByUserId(1)).thenReturn(null);
//		try {
//			userController.resetPassword(passDto, 1);
//		} catch (Exception e) {
//			String expectedMessage = "User Not Found";
//			Assert.assertEquals(expectedMessage, e.getMessage());
//		}
//	}
//
//	@Test
//	public void testResetPasswordForCheckPass() {
//		when(userDao.findByUserId(1)).thenReturn(user);
//		ApiResponse<User> resetPassword = userController.resetPassword(passDto, 1);
//		assertEquals(passDto, resetPassword.getResult());
//	}
//	@Test
//	public void testResetPasswordForCheckPassEqual() {
//		passDto.setPassword("swarup");
//		when(userDao.findByUserId(1)).thenReturn(user);
//		when(userService.updatePassWord(user, passDto)).thenReturn(user);
//		ApiResponse<User> resetPassword = userController.resetPassword(passDto, 1);
//		assertEquals(user, resetPassword.getResult());
//	}
//	@Test
//	public void testResetPasswordForCheckPassForNull() {
//		passDto.setPassword("swarup");
//		when(userDao.findByUserId(1)).thenReturn(user);
//		when(userService.updatePassWord(user, passDto)).thenReturn(null);
//		ApiResponse<User> resetPassword = userController.resetPassword(passDto, 1);
//		assertEquals(null, resetPassword.getResult());
//	}

}
