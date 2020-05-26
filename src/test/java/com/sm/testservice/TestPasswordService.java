package com.sm.testservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.sm.dao.UserDao;
import com.sm.dto.PasswordDto;
import com.sm.model.User;
import com.sm.service.impl.PasswordVerrificationServiceImpl;
import com.sm.util.EmailService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TestPasswordService {
	
	@InjectMocks
	PasswordVerrificationServiceImpl passService;
	
	@Mock
	UserDao userDao;
	
	@Mock
	private BCryptPasswordEncoder bcryptEncoder;
	
	@Mock
	EmailService emailService;
	
	User user = new User();
	PasswordDto passDto =new PasswordDto();
	@Before
	public void setUp() {
		user.setUserId(1);
		user.setUserEmail("swarup@gmail.com");
		user.setEmailVerifficationCode("111111");
		user.setUserPassword("swarup");
		
		passDto.setCnfPassword("swarup");
		passDto.setPassword("swarup");
	}
	
	@Test
	public void testVerifyCodeForNullUser() {
		when(userDao.findByUserEmail("swarup@gmail.com")).thenReturn(null);

		try{
			passService.verifyCode("swarup@gmail.com", "12345");
		}catch (Exception e) {
			String message ="User Not Found";
			assertEquals(message, e.getMessage());
		}
	}
	@Test
	public void testVerifyCodeForInvalidPass() {
		when(userDao.findByUserEmail("swarup@gmail.com")).thenReturn(user);
			User verifyCode = passService.verifyCode("swarup@gmail.com", "12345");
			assertEquals(null, verifyCode);
	}
	@Test
	public void testVerifyCodeForValidPass() {
		when(userDao.findByUserEmail("swarup@gmail.com")).thenReturn(user);
			User verifyCode = passService.verifyCode("swarup@gmail.com", "111111");
			assertEquals(user, verifyCode);
	}
	
	@Test
	public void changePassword() {
		when(bcryptEncoder.encode(user.getUserPassword())).thenReturn("swarup");
		when(userDao.save(user)).thenReturn(user);
		User changePassword = passService.changePassword(user, passDto);
		assertEquals(user, changePassword);
	}
	
//	@Test
//	public void testSendMailForSuccess() {
//		when(emailService.sentMail(user.getUserEmail(), any())).thenReturn(true);
//		when(userDao.save(any(User.class))).thenReturn(user);
//		boolean sendMail = passService.sendMail(user);
//		assertEquals(true, sendMail);
//		
//	}
	

}
