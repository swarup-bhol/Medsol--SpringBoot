package com.sm.service;


import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sm.dto.LoginUser;
import com.sm.dto.PasswordDto;
import com.sm.dto.Profile;
import com.sm.dto.ProfileDetailsDto;
import com.sm.dto.SuggetionsDto;
import com.sm.dto.UpdateProfileDto;
import com.sm.dto.UserDto;
import com.sm.model.User;

public interface UserService {

	User save(UserDto user);

	User findOne(String username);

	boolean checkUser(UserDto user);

	public User findByuserId(long userId);
	
	public boolean loginUser(LoginUser loginUser);
	
	User uploadProfilePic(MultipartFile file, User user) throws IOException;

	ProfileDetailsDto getProfileDetails(User user);

	ProfileDetailsDto updateProfile(long userId, UpdateProfileDto profileDto);

	User updatePassWord(User user, PasswordDto passwordDto);

	User createProfile(Profile profile, User user);

	List<SuggetionsDto> searchUser(String name,long userId);

	User uploadDocument(MultipartFile file, User user) throws IOException;
	
	
}
