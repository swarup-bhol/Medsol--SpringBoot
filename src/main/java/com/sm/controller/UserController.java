package com.sm.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.sm.util.ApiResponse;
import com.sm.util.Constants;
import com.sm.util.JwtTokenUtil;

@RestController
@RequestMapping("/api/medsol/v1")
public class UserController {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
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
	public ApiResponse<User> saveUser(@RequestBody UserDto user) {
		boolean userExist = userService.checkUser(user);
		if (!userExist) {
			return new ApiResponse<>(HttpStatus.OK.value(), Constants.CREATED, userService.save(user));
		}
		return new ApiResponse<>(409, Constants.USER_EXIST, user);
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
	public ApiResponse<JwtToken> loginUser(@RequestBody LoginUser loginUser)
			throws AuthenticationException, InvalidUserNamePasswordException {
		if (loginUser == null) {
			return new ApiResponse<>(401, Constants.INVALID_CREDENTIALS, true);
		}else {
			
		boolean result = userService.loginUser(loginUser);
		if (result) {
			final User user = userService.findOne(loginUser.getEmail());
			final String token = jwtTokenUtil.generateToken(user);
			return new ApiResponse<>(200, Constants.OK, new JwtToken(token, user.getUserEmail(), user.getUserId()));
		}
		return new ApiResponse<>(400, Constants.INVALID_CREDENTIALS, loginUser);
		}
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
	public  ApiResponse<JwtToken> createProfile(@RequestBody Profile profile, @PathVariable String email) {
		User user = userDao.findByUserEmail(email);
		if (user == null)
			throw new UserNotFound(Constants.USER_NOT_FOUND);
		if(user.isRecordStatus()) return new ApiResponse<>(409, Constants.USER_EXIST, profile);
		User updatedUser = userService.createProfile(profile, user);
		final String token = jwtTokenUtil.generateToken(user);
		return new ApiResponse<>(200, Constants.OK,
				new JwtToken(token, updatedUser.getUserEmail(), updatedUser.getUserId()));
	}

	/**
	 * @author swarup bhol
	 *
	 * @purpose  upload profile picture
	 *
	 *
	 * @param file
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/upload/profilePic/{userId}")
	public ApiResponse<User> uploadProfilePic(@RequestParam("file") MultipartFile file, @PathVariable long userId)
			throws IOException {
		if (file.isEmpty())
			return new ApiResponse<>(400, Constants.BAD_REQUEST, file);
		User user = userService.findByuserId(userId);
		if (user == null)
			throw new UserNotFound(Constants.USER_NOT_FOUND);
		User userProfile = userService.uploadProfilePic(file, user);
		return new ApiResponse<>(200, Constants.CREATED, userProfile);

	}

	/**
	 * 
	 * @param userId
	 * @return MediaType
	 * @throws IOException
	 */
	@GetMapping(value = "/profilePic/{userId}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
	public @ResponseBody byte[] getClassPath(@PathVariable Long userId) throws IOException {
		User user = userDao.findByUserId(userId);
		if (user == null || user.getProfilePicPath() == null)
			return null;
		Path uploadPath = Paths.get(user.getProfilePicPath());
		byte[] pic = Files.readAllBytes(uploadPath);
		return pic;
	}
	
	@PutMapping("/update/profile/{userId}")
	public ApiResponse<ProfileDetailsDto> updateProfileDetails(@RequestBody UpdateProfileDto profileDto,@PathVariable long userId){
		ProfileDetailsDto updateProfile = userService.updateProfile(userId,profileDto);
		return new ApiResponse<>(200, Constants.OK, updateProfile);
	}
	
	@PutMapping("/update/password/{userId}")
	public ApiResponse<User> resetPassword(@RequestBody PasswordDto passwordDto,@PathVariable long userId){
		User user = userDao.findByUserId(userId);
		if(user == null) throw new UserNotFound(Constants.USER_NOT_FOUND);
		if(! passwordDto.getPassword().equals(passwordDto.getCnfPassword()))
			return new ApiResponse<>(402, Constants.BAD_REQUEST, passwordDto);
		User users = userService.updatePassWord(user,passwordDto);
		if(users == null) { 
			return new ApiResponse<>(200, Constants.PASSWORD_NOT_MATCH, null);
		}else {
			return new ApiResponse<>(200, Constants.OK, users);
		}
		
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("profile/{userId}")
	public ApiResponse<ProfileDetailsDto> getprofileDetails(@PathVariable long userId){
		User user = userDao.findByUserId(userId);
		if(user == null) throw  new UserNotFound(Constants.USER_NOT_FOUND);
		ProfileDetailsDto  detailsDto = userService.getProfileDetails(user);
		return new ApiResponse<>(200, Constants.OK, detailsDto);
	}
	
	
	@GetMapping("/user/findBy/{userId}/{name}")
	public ApiResponse<List<SuggetionsDto>> searchUser(@PathVariable long userId,@PathVariable String name){
		List<SuggetionsDto> findUsers = userService.searchUser(name, userId);
		return new ApiResponse<>(200, Constants.OK, findUsers);
	}

	@PostMapping("/user/upload/document")
	public ApiResponse<User> uploadDocument(@RequestParam("file") MultipartFile file,@RequestParam("userId") long userId) throws IOException{
		User user = userService.findByuserId(userId);
		if (user == null)
			throw new UserNotFound(Constants.USER_NOT_FOUND);
		User updatedUser = userService.uploadDocument(file, user);
		return new ApiResponse<>(200, Constants.CREATED, updatedUser);
		
	}
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
