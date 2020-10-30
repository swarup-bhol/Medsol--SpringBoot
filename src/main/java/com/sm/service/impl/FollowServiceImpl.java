package com.sm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sm.dao.FollowDao;
import com.sm.dao.ProfessionDao;
import com.sm.dao.UserDao;
import com.sm.dto.PersonDto;
import com.sm.dto.SuggetionsDto;
import com.sm.exception.ResourceNotFoundException;
import com.sm.exception.UserNotFound;
import com.sm.model.Follower;
import com.sm.model.Profession;
import com.sm.model.User;
import com.sm.service.FollowService;
import com.sm.util.Constants;
import com.sm.util.MedsolResponse;

@Service
public class FollowServiceImpl implements FollowService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	FollowDao followDao;

	@Autowired
	UserDao userDao;

	@Autowired
	ProfessionDao professionDao;

	@Override
	public Follower followUser(long userId, long followerId) {
		Follower fo = new Follower();
		Follower follower = null;
		try {
			fo.setUserId(userId);
			fo.setFollowedBy(followerId);
			fo.setFollowing(true);
			follower = followDao.save(fo);
		} catch (HibernateException e) {
			logger.error("DB error : followUser");
		} catch (NullPointerException e) {
			logger.error("Null : followUser");
		} catch (Exception e) {
			logger.error("Exception :followUser");
		}
		return follower;
	}

	@Override
	public boolean isFollowing(long userId, long followId) {
		Follower followers = null;
		boolean isfollowing = false;
		try {
			followers = followDao.findByUserIdAndFollowedBy(followId, userId);
			if (followers == null || followers.isFollowing() == false)
				isfollowing = false;
			else
				isfollowing = true;
		} catch (HibernateException e) {
			logger.error("DB error : isFollowing");
		} catch (NullPointerException e) {
			logger.error("Null : isFollowing");
		} catch (Exception e) {
			logger.error("Exception :isFollowing");
		}
		return isfollowing;
	}

	@Override
	public List<SuggetionsDto> findAllFollowing(long userId) {
		List<Follower> followers = followDao.findByFollowedByAndIsFollowing(userId, true);
		List<SuggetionsDto> users = new ArrayList<SuggetionsDto>();
		try {
			if (followers != null) {
				users = followers.stream().map(follower -> {
					long follwerId = follower.getUserId();
					User user = userDao.findByUserId(follwerId);
					if (user == null)
						throw new UsernameNotFoundException(Constants.USER_NOT_FOUND);
					Profession proff = professionDao.findByProfessionId(user.getProfessionId());
					if (proff == null)
						throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
					return SuggetionsDto.builder().Profession(proff.getProfessionName())
							.isFollowing(isFollowing(userId, follwerId)).institute(user.getInstituteName())
							.userId(user.getUserId()).userName(user.getFullName()).build();

				}).collect(Collectors.toList());
			}
		} catch (HibernateException e) {
			logger.error("DB error : findAllFollowing");
		} catch (NullPointerException e) {
			logger.error("Null : findAllFollowing");
		} catch (Exception e) {
			logger.error("Exception :findAllFollowing");
		}
		return users;
	}

	@Override
	public List<SuggetionsDto> findAllFollowers(long userId) {
		List<Follower> followers = followDao.findByUserIdAndIsFollowing(userId, true);
		List<SuggetionsDto> users = new ArrayList<SuggetionsDto>();
		try {
			if (followers != null) {
				followers.stream().map(follower -> {
					long followedBy = follower.getFollowedBy();
					User user = userDao.findByUserId(followedBy);
					if (user == null)
						throw new UsernameNotFoundException(Constants.USER_NOT_FOUND);
					Profession profession = professionDao.findByProfessionId(user.getProfessionId());
					if (profession == null)
						throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
					return SuggetionsDto.builder().isFollowing(isFollowing(userId, followedBy))
							.institute(user.getInstituteName()).Profession(profession.getProfessionName())
							.userId(user.getUserId()).userName(user.getFullName()).build();

				}).collect(Collectors.toList());
			}
		} catch (HibernateException e) {
			logger.error("DB error : findAllFollowers");
		} catch (NullPointerException e) {
			logger.error("Null : findAllFollowers");
		} catch (Exception e) {
			logger.error("Exception :findAllFollowers");
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
	public List<SuggetionsDto> getAllUsersNotFollowedByCurrentUser(User user, int pageNo, int pageSize) {
		List<SuggetionsDto> suggetionsDtos = new ArrayList<SuggetionsDto>();
		List<Long> followList = new ArrayList<Long>();
		List<User> findAllUser = new ArrayList<User>();

		try {
			followList = followDao.findAllFolowerId(user.getUserId(), true);
			followList.add(user.getUserId()); // Excluding the current user
			findAllUser = userDao.findAllUser(followList, PageRequest.of(pageNo, pageSize));

			suggetionsDtos = findAllUser.stream().map(users -> {
				Profession profession = professionDao.findByProfessionId(users.getProfessionId());
				return SuggetionsDto.builder().userId(users.getUserId()).userName(users.getFullName())
						.institute(users.getInstituteName()).Profession(profession.getProfessionName())
						.isFollowing(isFollowing(user.getUserId(), users.getUserId())).build();
			}).collect(Collectors.toList());
		} catch (HibernateException e) {
			logger.error("DB error : getAllUsersNotFollowedByCurrentUser", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null : getAllUsersNotFollowedByCurrentUser", e.getMessage());
		} catch (Exception e) {
			logger.error("Exception :getAllUsersNotFollowedByCurrentUser", e.getMessage());
		}
		return suggetionsDtos;

	}

	@Override
	public MedsolResponse<Follower> follUsers(long userId, long followerId) {

		MedsolResponse<Follower> response = new MedsolResponse<>(true, 200, Constants.OK, null);

		try {
			User user = userDao.findByUserId(userId);
			User follower = userDao.findByUserId(followerId);
			if (user == null || follower == null)
				throw new UserNotFound(Constants.USER_NOT_FOUND);
			Follower followers = followDao.findByUserIdAndFollowedBy(userId, followerId);
			if (followers != null) {
				followers.setFollowing(true);
				response = new MedsolResponse<>(true, 200, Constants.OK, followDao.save(followers));
			}
			Follower follow = followUser(userId, followerId);
			response = new MedsolResponse<>(true, 200, Constants.OK, follow);
		} catch (HibernateException e) {
			logger.error("DB error : follUsers", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null : follUsers", e.getMessage());
		} catch (Exception e) {
			logger.error("Exception :follUsers", e.getMessage());
		}
		return response;
	}

	@Override
	public MedsolResponse<Follower> unfollowUser(long userId, long followerId) {
		MedsolResponse<Follower> response = new MedsolResponse<>(true, 200, Constants.OK, null);
		try {
			User user = userDao.findByUserId(userId);
			User follower = userDao.findByUserId(followerId);
			if (user == null || follower == null)
				throw new UserNotFound(Constants.USER_NOT_FOUND);
			Follower followers = followDao.findByUserIdAndFollowedBy(userId, followerId);
			if (followers != null) {
				followers.setFollowing(false);
				response = new MedsolResponse<>(true, 200, Constants.OK, followDao.save(followers));
			}
			response = new MedsolResponse<>(true, 200, Constants.USER_NOT_FOUND, followerId);
		} catch (HibernateException e) {
			logger.error("DB error : unfollowUser", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null : unfollowUser", e.getMessage());
		} catch (Exception e) {
			logger.error("Exception :unfollowUser", e.getMessage());
		}
		return response;
	}

	@Override
	public MedsolResponse<Boolean> isFollowingUser(long userId, long followId) {
		MedsolResponse<Boolean> response = new MedsolResponse<Boolean>(false, 200, Constants.OK, null);
		try {
			User user = userDao.findByUserId(userId);
			User follower = userDao.findByUserId(followId);
			if (user == null || follower == null)
				throw new UserNotFound(Constants.USER_NOT_FOUND);
			boolean isFollow = isFollowing(userId, followId);
			response = new MedsolResponse<Boolean>(true, 200, Constants.OK, isFollow);
		} catch (HibernateException e) {
			logger.error("DB error : isFollowingUser", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null : isFollowingUser", e.getMessage());
		} catch (Exception e) {
			logger.error("Exception :isFollowingUser", e.getMessage());
		}
		return response;
	}

	@Override
	public MedsolResponse<List<SuggetionsDto>> allFollower(long userId) {
		MedsolResponse<List<SuggetionsDto>> response = new MedsolResponse<>(false, 200, "Success", null);
		try {
			User user = userDao.findByUserId(userId);
			if (user == null)
				throw new UserNotFound(Constants.USER_NOT_FOUND);
			response = new MedsolResponse<>(true, 200, "Success", findAllFollowers(userId));
		} catch (HibernateException e) {
			logger.error("DB error : allFollower", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null : allFollower", e.getMessage());
		} catch (Exception e) {
			logger.error("Exception :allFollower", e.getMessage());
		}
		return response;
	}

	@Override
	public MedsolResponse<List<SuggetionsDto>> allFollowings(long userId) {

		MedsolResponse<List<SuggetionsDto>> response = new MedsolResponse<>(false, 200, "Success", null);
		try {
			User user = userDao.findByUserId(userId);
			if (user == null)
				throw new UserNotFound(Constants.USER_NOT_FOUND);
			List<SuggetionsDto> users = findAllFollowing(userId);
			response = new MedsolResponse<>(true, 200, "Success", users);
		} catch (

		HibernateException e) {
			logger.error("DB error : allFollower", e.getMessage());
		} catch (Exception e) {
			logger.error("Exception :allFollower", e.getMessage());
		}
		return response;
	}

	@Override
	public MedsolResponse<List<PersonDto>> unfolowers(long userId, int pageNo, int pageSize) {
		MedsolResponse<List<PersonDto>> response = new MedsolResponse<>(false, 200, Constants.OK, null);
		try {
			User user = userDao.findByUserId(userId);
			if (user == null)
				throw new UserNotFound(Constants.USER_NOT_FOUND);
			List<SuggetionsDto> allUser = getAllUsersNotFollowedByCurrentUser(user, pageNo, pageSize);
			response = new MedsolResponse<>(true, 200, Constants.OK, allUser);
		} catch (

		HibernateException e) {
			logger.error("DB error : allFollower", e.getMessage());
		} catch (Exception e) {
			logger.error("Exception :allFollower", e.getMessage());
		}
		return response;
	}
}
