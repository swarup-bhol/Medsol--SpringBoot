package com.sm.service;

import java.util.List;

import com.sm.dto.PersonDto;
import com.sm.dto.SuggetionsDto;
import com.sm.model.Follower;
import com.sm.model.User;
import com.sm.util.MedsolResponse;

public interface FollowService {

	Follower followUser(long userId, long followerId);

	boolean isFollowing(long userId, long followId);

	List<SuggetionsDto> findAllFollowing(long userId);

	List<SuggetionsDto> findAllFollowers(long userId);

//	List<User> findPeople(int pageNo);

	List<SuggetionsDto> getAllUsersNotFollowedByCurrentUser(User user,int pageNo, int pageSize);

	MedsolResponse<Follower> follUsers(long userId, long followerId);

	MedsolResponse<Follower> unfollowUser(long userId, long followerId);

	MedsolResponse<Boolean> isFollowingUser(long userId, long followId);

	MedsolResponse<List<SuggetionsDto>> allFollower(long userId);

	MedsolResponse<List<SuggetionsDto>> allFollowings(long userId);

	MedsolResponse<List<PersonDto>> unfolowers(long userId, int pageNo, int pageSize);
}



