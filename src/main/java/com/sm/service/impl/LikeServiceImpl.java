package com.sm.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.dao.LikeDao;
import com.sm.model.Likes;
import com.sm.model.Post;
import com.sm.model.User;
import com.sm.service.LikeService;

@Service
public class LikeServiceImpl implements LikeService {

	@Autowired
	LikeDao likeDao;

	@Override
	public Likes createNewLike(User user, Post post) {
		Likes likes = likeDao.findByPostAndUser(post, user);

		if (likes == null) {
			Likes like = new Likes();
			like.setUser(user); 
			like.setPost(post);
			like.setRecordStatus(true);
			return likeDao.save(like);

		} else {
			likes.setRecordStatus(true);
			return likeDao.save(likes);
		}

	}

	@Override
	public Likes updateUnlike(User user, Post post) {
		Likes likes = likeDao.findByPostAndUser(post, user);

		if (likes == null) {
			return null;
		}
		likes.setRecordStatus(false);
		return likeDao.save(likes);

	}

}
