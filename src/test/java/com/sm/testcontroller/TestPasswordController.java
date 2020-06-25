package com.sm.testcontroller;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.sm.controller.PasswordVerrificationController;
//import com.sm.dao.UserDao;
//import com.sm.dto.PasswordDto;
//import com.sm.model.User;
//import com.sm.service.PasswordVerrificationService;
//import com.sm.util.ApiResponse;
//import com.sm.util.Constants;
//import com.sm.util.EmailService;
//
//@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
public class TestPasswordController {

//	@InjectMocks
//	PasswordVerrificationController passController;
//
//	@Mock
//	UserDao userDao;
//
//	EmailService emailService;
//
//	@Mock
//	PasswordVerrificationService passwordService;
//
//	User user = new User();
//	PasswordDto passDto =new PasswordDto();
//
//	@Before
//	public void setUp() {
//
//		user.setUserId(1);
//		user.setUserEmail("swarup@gmail.com");
//		user.setFullName("swarup bhol");
//		passDto.setCnfPassword("swarup");
//		passDto.setPassword("swarup");
//	}
//
//	@Test
//	public void testCheckEmailOrPasswordForInvalidEmail() {
//		when(userDao.findByUserEmail("swarup@gmail.com")).thenReturn(null);
//
//		try {
//			passController.checkEmailOrPassword("swarup@gmail.com");
//		} catch (Exception e) {
//			String message = "User Not Found";
//			assertEquals(message, e.getMessage());
//		}
//
//	}
//
//	@Test
//	public void testCheckEmailOrPasswordForValidEmail() {
//		when(userDao.findByUserEmail("swarup@gmail.com")).thenReturn(user);
//		when(passwordService.sendMail(user)).thenReturn(true);
//		ApiResponse<String> checkEmailOrPassword = passController.checkEmailOrPassword("swarup@gmail.com");
//		assertThat(checkEmailOrPassword);
//
//	}
//
//	@Test
//	public void verifyPasswordForNotMatch() {
//		when(passwordService.verifyCode("swarup@gmail.com", "429999")).thenReturn(null);
//		ApiResponse<User> verifyPassword = passController.verifyPassword("swarup@gmail.com", "429999");
//		assertEquals(Constants.BAD_REQUEST, verifyPassword.getMessage());
//	}
//	@Test
//	public void verifyPasswordForMatch() {
//		when(passwordService.verifyCode("swarup@gmail.com", "429999")).thenReturn(user);
//		ApiResponse<User> verifyPassword = passController.verifyPassword("swarup@gmail.com", "429999");
//		assertEquals(user, verifyPassword.getResult());
//	}
//	
//	@Test
//	public void updatePasswordForNullUser() {
//		when(userDao.findByUserId(1)).thenReturn(null);
//		try {
//			passController.updatePassword(passDto,1);
//		} catch (Exception e) {
//			String message = "User Not Found";
//			assertEquals(message, e.getMessage());
//		}
//	}
//	@Test
//	public void updatePasswordForPasswordNotSame() {
//		passDto.setCnfPassword("sadsad");
//		when(userDao.findByUserId(1)).thenReturn(user);
//		ApiResponse<User> updatePassword = passController.updatePassword(passDto,1);
//		assertEquals(402, updatePassword.getStatus());
//	}
//	
//	@Test
//	public void updatePasswordForPasswordSame() {
//		when(userDao.findByUserId(1)).thenReturn(user);
//		ApiResponse<User> updatePassword = passController.updatePassword(passDto,1);
//		assertEquals(200, updatePassword.getStatus());
//	}

}
