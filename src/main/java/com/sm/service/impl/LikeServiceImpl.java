package com.sm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sm.dao.LikeDao;
import com.sm.model.Comment;
import com.sm.model.Likes;
import com.sm.model.Post;
import com.sm.model.User;
import com.sm.service.LikeService;
import com.sm.service.NotificationService;

@Service
public class LikeServiceImpl implements LikeService {
	public static final String LIKE = "like";

	@Autowired
	LikeDao likeDao;


	@Autowired
	NotificationService notificationService;

	@Override
	public Likes createNewLike(User user, Post post) {
		Likes likes = likeDao.findByPostAndUserAndCommentId(post, user,0);

		if (likes == null) {
			Likes like = new Likes();
			like.setUser(user);
			like.setPost(post);
			like.setRecordStatus(true);
			notificationService.createNotification(post, user, LIKE);
			return likeDao.save(like);

		} else {
			likes.setRecordStatus(true);
//			notificationService.createNotification(post, user,LIKE);
			return likeDao.save(likes);
		}

	}

	@Override
	public Likes updateUnlike(User user, Post post) {
		Likes likes = likeDao.findByPostAndUserAndCommentId(post, user,0);
		if (likes == null) {
			return null;
		}
		likes.setRecordStatus(false);

		return likeDao.save(likes);

	}

	@Override
	public Likes createNewCommentLike(Comment comment, User user) {
		Likes like = likeDao.findByPostAndUserAndCommentId(comment.getPost(), user,comment.getCommentId());
		if (like != null) {
			like.setRecordStatus(true);
			return likeDao.save(like);
		} else {
			Likes like1 = new Likes();
			like1.setCommentId(comment.getCommentId());
			like1.setPost(comment.getPost());
			like1.setRecordStatus(true);
			like1.setUser(user);
			return likeDao.save(like1);
		}
	}

	@Override
	public Likes updateCommentUnlike(User user, Comment comment) {
		Likes like = likeDao.findByPostAndUserAndCommentId(comment.getPost(), user,comment.getCommentId());
		if (like == null) {
			return null;
		}
		like.setRecordStatus(false);		
		return likeDao.save(like);
	}

}
