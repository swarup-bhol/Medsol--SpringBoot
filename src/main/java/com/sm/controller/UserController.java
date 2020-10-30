package com.sm.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sm.dao.UserDao;
import com.sm.dto.JwtToken;
import com.sm.dto.LoginUser;
import com.sm.dto.PasswordDto;
import com.sm.dto.Profile;
import com.sm.dto.ProfileDetailsDto;
import com.sm.dto.SuggetionsDto;
import com.sm.dto.UpdateProfileDto;
import com.sm.dto.UserDto;
import com.sm.exception.InvalidUserNamePasswordException;
import com.sm.exception.UserNotFound;
import com.sm.model.User;
import com.sm.service.UserService;
import com.sm.util.MedsolResponse;
import com.sm.util.Constants;

@RestController
@RequestMapping("/api/medsol/v1")
public class UserController {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@Autowired
	UserDao userDao;

	/**
	 * @author swarup bhol
	 *
	 * @purpose create a new user
	 * @param user
	 * @return
	 */
	@PostMapping("/register")
	public MedsolResponse<User> saveUser(@RequestBody UserDto user) {
		return userService.createNewUser(user);
	}

	/**
	 *
	 * @author swarup
	 *
	 * @purpose login an user
	 * 
	 * @param loginUser
	 * @return
	 * @throws AuthenticationException
	 * @throws InvalidUserNamePasswordException
	 */
	@PostMapping("/login")
	public MedsolResponse<JwtToken> loginUser(@RequestBody LoginUser loginUser)
			throws AuthenticationException, InvalidUserNamePasswordException {
		return userService.login(loginUser);
	}

	/**
	 * @author swarup
	 *
	 * @purpose create new profile
	 * @param profileInfo
	 * @param email
	 * @return
	 */
	@PostMapping("/profile/create/{email}")
	public MedsolResponse<JwtToken> createProfile(@RequestBody Profile profile, @PathVariable String email) {
		return userService.createProfile(profile, email);
	}


	/**
	 * @author swarup bhol
	 *
	 * @purpose upload profile picture
	 *
	 *
	 * @param file
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/upload/profilePic/{userId}")
	public MedsolResponse<User> uploadProfilePic(@RequestParam("file") MultipartFile file, @PathVariable long userId)
			throws IOException {
		if (file.isEmpty())
			return new MedsolResponse<>(false, 400, Constants.BAD_REQUEST, file);
		User user = userService.findByuserId(userId);
		if (user == null)
			throw new UserNotFound(Constants.USER_NOT_FOUND);
		return userService.uploadProfilePic(file, user);

	}

	/**
	 * @author swarupb
	 * 
	 * @param userId
	 * @return MediaType
	 * @throws IOException
	 */
	@GetMapping(value = "/profilePic/{userId}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	public @ResponseBody byte[] getClassPath(@PathVariable Long userId) throws IOException {
		User user = userDao.findByUserId(userId);
		if (user == null || user.getProfilePicPath() == null || user.getProfilePicPath().equals("0"))
			return null;
		Path uploadPath = Paths.get(user.getProfilePicPath());
		byte[] pic = Files.readAllBytes(uploadPath);
		return pic;
	}

	
	
	/**
	 * @author swarupb
	 * 
	 * @param profileDto
	 * @param userId
	 * @return
	 */
	@PutMapping("/update/profile/{userId}")
	public MedsolResponse<ProfileDetailsDto> updateProfileDetails(@RequestBody UpdateProfileDto profileDto,
			@PathVariable long userId) {
		return userService.updateProfile(userId, profileDto);
	}

	
	/**
	 * @author swarupb
	 * 
	 * @param passwordDto
	 * @param userId
	 * @return
	 */
	@PutMapping("/update/password/{userId}")
	public MedsolResponse<User> resetPassword(@RequestBody PasswordDto passwordDto, @PathVariable long userId) {	
		User user = userDao.findByUserId(userId);
		if (user == null)
			throw new UserNotFound(Constants.USER_NOT_FOUND);
		if (!passwordDto.getPassword().equals(passwordDto.getCnfPassword()))
			return new MedsolResponse<>(false, 402, Constants.BAD_REQUEST, passwordDto);		
		return userService.updatePassWord(user, passwordDto);
	}


	/**
	 * @author swarupb
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("profile/{userId}")
	public MedsolResponse<ProfileDetailsDto> getprofileDetails(@PathVariable long userId) {
		User user = userDao.findByUserId(userId);
		if (user == null)
			throw new UserNotFound(Constants.USER_NOT_FOUND);
		return userService.getProfileDetails(user);
	}

	
	
	/**
	 * @author swarupb
	 * 
	 * @param userId
	 * @param name
	 * @return
	 */
	@GetMapping("/user/findBy/{userId}/{name}")
	public MedsolResponse<List<SuggetionsDto>> searchUser(@PathVariable long userId, @PathVariable String name) {
		return userService.searchUser(name, userId);
	}

	
	/**
	 * @author swarupb
	 * 
	 * @param file
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/user/upload/document")
	public MedsolResponse<User> uploadDocument(@RequestParam("file") MultipartFile file,
			@RequestParam("userId") long userId) throws IOException {
		User user = userService.findByuserId(userId);
		if (user == null)
			throw new UserNotFound(Constants.USER_NOT_FOUND);
		return userService.uploadDocument(file, user);
	}
	
	

	/**
	 * @author swarupb
	 * 
	 * 
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/user/document/{userId}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	public @ResponseBody byte[] getDocument(@PathVariable Long userId) throws IOException {
		User user = userDao.findByUserId(userId);
		if (user == null || user.getUserDocumentPath() == null)
			return null;
		Path uploadPath = Paths.get(user.getUserDocumentPath());
		byte[] pic = Files.readAllBytes(uploadPath);
		return pic;
	}
}
