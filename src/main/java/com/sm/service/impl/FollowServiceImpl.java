package com.sm.service.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sm.dao.FollowDao;
import com.sm.dao.ProfessionDao;
import com.sm.dao.UserDao;
import com.sm.dto.SuggetionsDto;
import com.sm.exception.ResourceNotFoundException;
import com.sm.model.Follower;
import com.sm.model.Profession;
import com.sm.model.User;
import com.sm.service.FollowService;
import com.sm.util.Constants;

@Service
public class FollowServiceImpl implements FollowService {

	@Autowired
	FollowDao followDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	ProfessionDao professionDao;

	@Override
	public Follower followUser(long userId, long followerId) {
		Follower fo = new Follower();
		fo.setUserId(userId);
		fo.setFollowedBy(followerId);
		fo.setFollowing(true);
		return followDao.save(fo);
	}

	@Override
	public boolean isFollowing(long userId, long followId) {
		Follower followers = followDao.findByUserIdAndFollowedBy( followId,userId);
		if(followers == null || followers.isFollowing() == false)
		return false;
		else return true;
	}

	@Override
	public List<SuggetionsDto> findAllFollowing(long userId) {
		List<Follower> followers = followDao.findByFollowedByAndIsFollowing(userId, true);
		List<SuggetionsDto> users = new ArrayList<SuggetionsDto>();
		if(followers != null) {
			Iterator<Follower> follower = followers.iterator();
			while (follower.hasNext()) {
				SuggetionsDto suggetionsDto = new SuggetionsDto();
				long follwerId = follower.next().getUserId();
				User user = userDao.findByUserId(follwerId);
				if(user == null) throw new UsernameNotFoundException(Constants.USER_NOT_FOUND);
				Profession proff =  professionDao.findByProfessionId(user.getProfessionId());
				if(proff == null) throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
				suggetionsDto.setProfession(proff.getProfessionName());
				suggetionsDto.setFollowing(isFollowing(userId,follwerId));
				suggetionsDto.setInstitute(user.getInstituteName());
				suggetionsDto.setUserId(user.getUserId());
				suggetionsDto.setUserName(user.getFullName());
				users.add(suggetionsDto);
			}
			
		}
		return users;
	}

	@Override
	public List<SuggetionsDto> findAllFollowers(long userId) {
		List<Follower> followers = followDao.findByUserIdAndIsFollowing(userId, true);
		List<SuggetionsDto> users = new ArrayList<SuggetionsDto>();
		if(followers != null) {
			Iterator<Follower> follower = followers.iterator();
			while (follower.hasNext()) {
				SuggetionsDto suggetionsDto = new SuggetionsDto();
				long followedBy = follower.next().getFollowedBy();
				User user = userDao.findByUserId(followedBy);
				if(user == null) throw new UsernameNotFoundException(Constants.USER_NOT_FOUND);
				Profession profession = professionDao.findByProfessionId(user.getProfessionId());
				if(profession== null) throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
				suggetionsDto.setFollowing(isFollowing(userId,followedBy));
				suggetionsDto.setInstitute(user.getInstituteName());
				suggetionsDto.setProfession(profession.getProfessionName());
				suggetionsDto.setUserId(user.getUserId());
				suggetionsDto.setUserName(user.getFullName());
				users.add(suggetionsDto);
			}	
		}
		return users;
	}

//	@Override
//	public List<User> findPeople(int pageNo) {
//		final int pageSize = 10;
//		Pageable pageable = PageRequest.of(pageNo,pageSize,Sort.by("name"));
//		Page<User> pageLIst = userDao.findAll(pageable);
//		if(pageLIst.hasContent()) {
//			return pageLIst.getContent();
//		}
//		return new ArrayList<User>();
//	}

	@Override
	public List<SuggetionsDto> getAllUsersNotFollowedByCurrentUser(User user,int pageNo, int pageSize) {
		
		List<Long> followList = followDao.findAllFolowerId(user.getUserId(), true);
		followList.add(user.getUserId()); // Excluding the current user
		List<User> findAllUser = userDao.findAllUser(followList, PageRequest.of(pageNo,pageSize));
		List<SuggetionsDto> suggetionsDtos = new ArrayList<SuggetionsDto>();
		if(! findAllUser.isEmpty()) {
			Iterator<User> iterator = findAllUser.iterator();
			while (iterator.hasNext()) {
				User users = iterator.next();
				Profession profession = professionDao.findByProfessionId(users.getProfessionId());
				SuggetionsDto suggetionsDto = new SuggetionsDto();
				suggetionsDto.setUserId(users.getUserId());
				suggetionsDto.setUserName(users.getFullName());
				suggetionsDto.setInstitute(users.getInstituteName());
				suggetionsDto.setProfession(profession.getProfessionName());
				suggetionsDto.setFollowing(isFollowing(user.getUserId(), users.getUserId()));
				suggetionsDtos.add(suggetionsDto);
			}
		}
		return suggetionsDtos;

	}



}
