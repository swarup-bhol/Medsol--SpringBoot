package com.sm.testservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.mockito.ArgumentMatchers.any;

import com.sm.dao.FollowDao;
import com.sm.dao.PostDao;
import com.sm.dao.ProfessionDao;
import com.sm.dao.SpecializationDao;
import com.sm.dao.SubSpecializationDao;
import com.sm.dao.UserDao;
import com.sm.dto.LoginUser;
import com.sm.dto.ProfileDetailsDto;
import com.sm.dto.UserDto;
import com.sm.model.Profession;
import com.sm.model.Specialization;
import com.sm.model.User;
import com.sm.service.impl.UserServiceImpl;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TestUserService {
	@InjectMocks
	UserServiceImpl userService;
	
	@Mock
	UserDao userDao;
	
	@Mock
	ProfessionDao professiondao;
	
	@Mock
	SpecializationDao specializationdao;
	
	@Mock
	SubSpecializationDao detailsPecDao;
	
	@Mock
	FollowDao followDao;
	
	@Mock
	PostDao postdao;
//	
//	@Mock
//	private BCryptPasswordEncoder bcryptEncoder;
//	
//	User user = new User();
//	UserDto userDto =new UserDto();
//	LoginUser loginUser = new LoginUser();
//	ProfileInfo profileInfo =new ProfileInfo();
//	Profession profession =new Profession();
//	Specialization specialization = new Specialization();
//	ProfileDetailsDto detailsDto = new ProfileDetailsDto();
//	DetailsSpecialization detailsSpec = new DetailsSpecialization();
//
//	
//	@Before
//	public void setUp() {
//		user.setUserEmail("gfj@fhd.com");
//		user.setUserPassword("swarup");
//		user.setProfessionId(1);
//		user.setSpecializationId(1);
//		user.setDetailsSpecializationId(1);
//		userDto.setEmail("fhjk");
//		userDto.setId(1);
//		userDto.setPassword("asdfgh@sd.wd");
//		userDto.setMobile("1234567890");
//		userDto.setName("sdfhjkl hj");
//		loginUser.setEmail("swarupbhol34@gmail.com");
//		loginUser.setPassword("swarup");
//		profileInfo.setGrade("Doctor");
//		profileInfo.setInstitute("Utkal University");
//		profileInfo.setProfession(1);
//		profileInfo.setSpecialty(1);
//		profileInfo.setSubspecialties(1);
//		
//		profession.setId(1);
//		profession.setName("Doctor");
//		
//		specialization.setId(1);
//		specialization.setName("Dentist");
//		specialization.setProfession(profession);
//		
//		detailsSpec.setId(1);
//		detailsSpec.setName("fgn gfh");
//		
//		detailsDto.setDetailsSpecialization(detailsSpec.getName());
//		detailsDto.setFollowerCount(2);
//		detailsDto.setFollowingCount(2);
//		detailsDto.setPostCount(2);
//		detailsDto.setProfession(profession.getName());
//		detailsDto.setSpecialization(specialization.getName());
//		detailsDto.setUser(user);
//	}
//	
//	@Test
//	public void testLoadUserByuserName() {	
//		when(userDao.findByUserEmail("gfj@fhd.com")).thenReturn(user);
//		UserDetails loadUserByUsername = userService.loadUserByUsername("gfj@fhd.com");
//		assertEquals(user.getUserEmail(), loadUserByUsername.getUsername());
//		
//	}
//	
//	@Test
//	public void testLoadUserByuserNameIfNullUser() {	
//		when(userDao.findByUserEmail("gfj@fhd.com")).thenReturn(null);
//		try{
//			userService.loadUserByUsername("gfj@fhd.com");
//		}catch (Exception e) {
//			String message = "Invalid email or password.";
//			assertEquals(message, e.getMessage());
//		}
//	}
//	
//	@Test
//	public void testSaveUser() {
//		when(userDao.save(any(User.class))).thenReturn(user);
//		when(bcryptEncoder.encode(userDto.getPassword())).thenReturn("swarup");
//		User users = userService.save(userDto);
//		assertEquals(user, users); 
//		
//	}
//	
//	@Test
//	public void testFindOneForNotNullUser() {
//		when(userDao.findByUserEmail("swarupbhol34@gmail.com")).thenReturn(user);
//		User findOne = userService.findOne("swarupbhol34@gmail.com");
//		assertEquals(user, findOne);
//	}
//	
//	@Test
//	public void testFindOneForNullUser() {
//		when(userDao.findByUserEmail("swarupbhol34@gmail.com")).thenReturn(null);
//		try {
//			userService.findOne("swarupbhol34@gmail.com");
//		}catch (Exception e) {
//			String message = "Invalid email"+"swarupbhol34@gmail.com";
//			assertEquals(message, e.getMessage());
//		}
//	}
//	
//	@Test
//	public void testCheckUser() {
//		when(userDao.findByUserEmail(userDto.getEmail())).thenReturn(user);
//		boolean checkUser = userService.checkUser(userDto);
//		assertThat(checkUser);
//		
//	}
//	@Test
//	public void testCheckUserIFNotExist() {
//		when(userDao.findByUserEmail(userDto.getEmail())).thenReturn(null);
//		boolean checkUser = userService.checkUser(userDto);
//		assertThat(checkUser);
//		
//	}
//	
//	@Test
//	public void testByUserId() {
//		when(userDao.findByUserId(1)).thenReturn(user);
//		
//		User findByuserId = userService.findByuserId(1);
//		assertEquals(user, findByuserId);
//	}
//	
//	@Test
//	public void testByUserIdForNull() {
//		when(userDao.findByUserId(1)).thenReturn(null);
//		
//		try{
//			userService.findByuserId(1);
//		}catch (Exception e) {
//			String message ="User Not Found"+1;
//			assertEquals(message , e.getMessage());
//		}
//		
//	}
//	
//	@Test
//	public void testloginUser() {
//		
//		when(userDao.findByUserEmail(loginUser.getEmail())).thenReturn(user);
//		when(bcryptEncoder.matches("swarup", user.getUserPassword())).thenReturn(true);
//		
//		boolean login = userService.loginUser(loginUser);
//		assertThat(login);
//		
//	}
//	@Test
//	public void testloginUserForIncorrectPassword() {
//		
//		when(userDao.findByUserEmail(loginUser.getEmail())).thenReturn(user);
//		when(bcryptEncoder.matches("swarup", user.getUserPassword())).thenReturn(false);
//		
//		boolean login = userService.loginUser(loginUser);
//		assertThat(login);
//		
//	}
//
//	@Test
//	public void testloginUserForNullUser() {
//		
//		when(userDao.findByUserEmail(loginUser.getEmail())).thenReturn(null);		
//		boolean login = userService.loginUser(loginUser);
//		assertThat(login);
//		
//	}
//	
//	@Test
//	public void testCreateProfile() {
//		when(userDao.save(user)).thenReturn(user);	
//		User createProfile = userService.createProfile(profileInfo, user);
//		assertEquals(user, createProfile);
//		
//	}
//	
//	@Test
//	public void testUploadProfilePic() throws IOException {
//		when(userDao.save(user)).thenReturn(user).thenReturn(user);
//		 MockMultipartFile file = new MockMultipartFile("file", "filename.png", "img/png", "some img".getBytes());
//		 User uploadProfilePic = userService.uploadProfilePic(file, user);
//		 assertEquals(uploadProfilePic, user);	
//	}
//	
//	@Test
//	public void testGetProfileDetailsForNullProf() {
//		when(professiondao.findById(1)).thenReturn(null);
//		try {
//			userService.getProfileDetails(user);
//		}catch (Exception e) {
//			String message = "Resource not found";
//			assertEquals(message, e.getMessage());
//		}
//		
//	}
//	@Test
//	public void testGetProfileDetailsForNullSpec() {
//		when(professiondao.findById(1)).thenReturn(profession);
//		when(specializationdao.findById(1)).thenReturn(null);
//		
//		try {
//			userService.getProfileDetails(user);
//		}catch (Exception e) {
//			String message = "Resource not found";
//			assertEquals(message, e.getMessage());
//		}
//		
//	}
//	@Test
//	public void testGetProfileDetailsForNullDetSpc() {
//		when(professiondao.findById(1)).thenReturn(profession);
//		when(specializationdao.findById(1)).thenReturn(specialization);
//		when(detailsPecDao.findById(1)).thenReturn(null);
//		
//		try {
//			userService.getProfileDetails(user);
//		}catch (Exception e) {
//			String message = "Resource not found";
//			assertEquals(message, e.getMessage());
//		}
//		
//	}
//	
//	@Test
//	public void testGetProfile() {
//		when(professiondao.findById(1)).thenReturn(profession);
//		when(specializationdao.findById(1)).thenReturn(specialization);
//		when(detailsPecDao.findById(1)).thenReturn(detailsSpec);
//		when(postdao.countByUserAndRecordStatus(user,true)).thenReturn((long) 2);
//		when(followDao.totalCountByFollowedByAndIsFollowing(user.getUserId(),true)).thenReturn((long) 2);
//		when(followDao.totalCountByUserIdAndIsFollowing(user.getUserId(),true)).thenReturn((long) 2);
//		ProfileDetailsDto profileDetails = userService.getProfileDetails(user);
//		assertEquals(detailsDto.getDetailsSpecialization(),profileDetails.getDetailsSpecialization() );
//		assertEquals(detailsDto.getFollowerCount(),profileDetails.getFollowerCount());
//	}

}
