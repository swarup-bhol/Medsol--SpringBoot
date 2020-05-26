package com.sm.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.dao.FollowDao;
import com.sm.dao.UserDao;
import com.sm.dto.PersonDto;
import com.sm.dto.SuggetionsDto;
import com.sm.exception.UserNotFound;
import com.sm.model.Follower;
import com.sm.model.User;
import com.sm.service.FollowService;
import com.sm.util.ApiResponse;
import com.sm.util.Constants;
import com.sm.util.JwtTokenUtil;

@RestController
@RequestMapping("/api/user")
public class FollowControler {

	@Autowired
	FollowService followService;

	@Autowired
	JwtTokenUtil util;
	
	@Autowired
	FollowDao dao;
	
	@Autowired
	UserDao userDao;
	

	
	// Follow a user
	@PostMapping("/{userId}/follow/{followerId}")
	public ApiResponse<Follower> follow(@PathVariable long userId, @PathVariable long followerId) {
		User user =  userDao.findByUserId(userId);
		User follower = userDao.findByUserId(followerId);
		if(user == null || follower== null) throw new UserNotFound(Constants.USER_NOT_FOUND);
		Follower followers = dao.findByUserIdAndFollowedBy(userId, followerId);
		if(followers != null) {
			followers.setFollowing(true);
			return new ApiResponse<>(200, Constants.OK,dao.save(followers));
		}
		Follower follow = followService.followUser(userId, followerId);
		return new ApiResponse<>(200, Constants.OK, follow);
	}
	
	
	// unFollow a user
	@PutMapping("/{userId}/unFollow/{followerId}")
	public ApiResponse<Follower> unFollow(@PathVariable long userId, @PathVariable long followerId) {
		User user =  userDao.findByUserId(userId);
		User follower = userDao.findByUserId(followerId);
		if(user == null || follower== null) throw new UserNotFound(Constants.USER_NOT_FOUND);
		Follower followers = dao.findByUserIdAndFollowedBy(userId, followerId);
		if(followers != null) {
			followers.setFollowing(false);
			Follower save = dao.save(followers);
			return new ApiResponse<>(200, Constants.OK,save);
		}
		return new ApiResponse<>(200, Constants.USER_NOT_FOUND, followerId);
	}
 // User is following or not
	@GetMapping("/{userId}/isFollow/{followId}")
	public ApiResponse<Boolean> isFollowing(@PathVariable long userId,@PathVariable long followId) {
		User user =  userDao.findByUserId(userId);
		User follower = userDao.findByUserId(followId);
		if(user == null || follower == null) throw new UserNotFound(Constants.USER_NOT_FOUND);
		boolean isFollow = followService.isFollowing(userId,followId);
		return new ApiResponse<Boolean>(200, Constants.OK, isFollow);
	}

	//  Get all followers
	@GetMapping("/{userId}")
	public ApiResponse<List<SuggetionsDto>> getAllFollower(@PathVariable long userId) {
		User user =  userDao.findByUserId(userId);
		if(user == null) throw new UserNotFound(Constants.USER_NOT_FOUND);
		List<SuggetionsDto> users = followService.findAllFollowers(userId);
		return new ApiResponse<>(200, "Success", users);
	}
	
	// get all persons the user is following
	@GetMapping("following/{userId}")
	public ApiResponse<List<SuggetionsDto>> getAllFollowingUser(@PathVariable long userId) {
		User user =  userDao.findByUserId(userId);
		if(user == null) throw new UserNotFound(Constants.USER_NOT_FOUND);
		List<SuggetionsDto> users = followService.findAllFollowing(userId);
		return new ApiResponse<>(200, "Success", users);
	}
	
//	@GetMapping("/peoples/{pageNo}")
//	public ApiResponse<List<User>> getPeople(@PathVariable int pageNo){
//		List<User> users = followService.findPeople(pageNo);
//		return new ApiResponse<>(200, Constants.OK, users);
//	}
	
	@GetMapping("/{userId}/peoples/{pageNo}/{pageSize}")
	public ApiResponse<List<PersonDto>> getAllUnfollowUser(@PathVariable long userId,@PathVariable int pageNo,@PathVariable int pageSize ){
		User user = userDao.findByUserId(userId);
		if(user == null) throw new UserNotFound(Constants.USER_NOT_FOUND);
		List<SuggetionsDto> allUser = followService.getAllUsersNotFollowedByCurrentUser(user, pageNo, pageSize);
		return new ApiResponse<>(200, Constants.OK, allUser);
	}

}
