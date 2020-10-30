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
import com.sm.util.MedsolResponse;
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
	/**
	 * @author swarupb
	 * 
	 * @param userId
	 * @param followerId
	 * @return
	 */
	@PostMapping("/{userId}/follow/{followerId}")
	public MedsolResponse<Follower> follow(@PathVariable long userId, @PathVariable long followerId) {
		return followService.follUsers(userId, followerId);
	}

	
	// unFollow a user/**
	/**
	 * @author swarupb
	 * 
	 * @param userId
	 * @param followerId
	 * @return
	 */
	@PutMapping("/{userId}/unFollow/{followerId}")
	public MedsolResponse<Follower> unFollow(@PathVariable long userId, @PathVariable long followerId) {
		return followService.unfollowUser(userId, followerId);
	}

 // User is following or not
	/**
	 * @author swarupb
	 * 
	 * @param userId
	 * @param followId
	 * @return
	 */
	@GetMapping("/{userId}/isFollow/{followId}")
	public MedsolResponse<Boolean> isFollowing(@PathVariable long userId,@PathVariable long followId) {
		return followService.isFollowingUser(userId, followId);
	}


	//  Get all followers
	/**
	 * @author swarupb
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("/{userId}")
	public MedsolResponse<List<SuggetionsDto>> getAllFollower(@PathVariable long userId) {
		return followService.allFollower(userId);
	}

	
	// get all persons the user is following
	/**
	 * @author swarupb
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("following/{userId}")
	public MedsolResponse<List<SuggetionsDto>> getAllFollowingUser(@PathVariable long userId) {
		return followService.allFollowings(userId);
	}


	
//	@GetMapping("/peoples/{pageNo}")
//	public ApiResponse<List<User>> getPeople(@PathVariable int pageNo){
//		List<User> users = followService.findPeople(pageNo);
//		return new ApiResponse<>(200, Constants.OK, users);
//	}
	/**
	 * @author swarupb
	 * 
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/{userId}/peoples/{pageNo}/{pageSize}")
	public MedsolResponse<List<PersonDto>> getAllUnfollowUser(@PathVariable long userId,@PathVariable int pageNo,@PathVariable int pageSize ){
		return followService.unfolowers(userId, pageNo, pageSize);
	}



}
