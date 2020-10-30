package com.sm.service.impl;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sm.dao.CommentDao;
import com.sm.dao.LikeDao;
import com.sm.dao.PostDao;
import com.sm.dao.UserDao;
import com.sm.exception.PostNotFoundException;
import com.sm.exception.ResourceNotFoundException;
import com.sm.model.Comment;
import com.sm.model.Likes;
import com.sm.model.Post;
import com.sm.model.User;
import com.sm.service.LikeService;
import com.sm.service.NotificationService;
import com.sm.util.Constants;
import com.sm.util.MedsolResponse;

@Service
public class LikeServiceImpl implements LikeService {
	public static final String LIKE = "like";
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PostDao postDao;

	@Autowired
	UserDao userDao;

	@Autowired
	LikeDao likeDao;

	@Autowired
	LikeService likeService;

	@Autowired
	CommentDao commentDao;

	@Autowired
	NotificationService notificationService;

	@Override
	public Likes createNewLike(User user, Post post) {
		Likes likes = null;

		try {
			likes = likeDao.findByPostAndUserAndCommentId(post, user, 0);
			if (likes == null) {
				Likes like = Likes.builder().user(user).post(post).recordStatus(true).build();
				likes = likeDao.save(like);
				notificationService.createNotification(post, user, LIKE);
			} else {
				likes.setRecordStatus(true);
				notificationService.createNotification(post, user, LIKE);
				likes = likeDao.save(likes);
			}
		} catch (HibernateException e) {
			logger.error("DB:error :createNewLike", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null :createNewLike", e.getMessage());
		} catch (Exception e) {
			logger.error("Error :createNewLike", e.getMessage());
		}
		return likes;

	}

	@Override
	public Likes updateUnlike(User user, Post post) {
		Likes likes = null;

		try {
			likes = likeDao.findByPostAndUserAndCommentId(post, user, 0);
			if (likes == null) {
				return null;
			}
			likes.setRecordStatus(false);
			likes = likeDao.save(likes);
		} catch (HibernateException e) {
			logger.error("DB:error :updateUnlike", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null :updateUnlike", e.getMessage());
		} catch (Exception e) {
			logger.error("Error :updateUnlike", e.getMessage());
		}
		return likes;

	}

	@Override
	public Likes createNewCommentLike(Comment comment, User user) {
		Likes like = null;
		try {
			like = likeDao.findByPostAndUserAndCommentId(comment.getPost(), user, comment.getCommentId());
			if (like != null) {
				like.setRecordStatus(true);
				return likeDao.save(like);
			} else {
				Likes likes = Likes.builder().commentId(comment.getCommentId()).post(comment.getPost())
						.recordStatus(true).user(user).build();
				like = likeDao.save(likes);
			}
		} catch (HibernateException e) {
			logger.error("DB:error :createNewCommentLike", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null :createNewCommentLike", e.getMessage());
		} catch (Exception e) {
			logger.error("Error :createNewCommentLike", e.getMessage());
		}
		return like;
	}

	@Override
	public Likes updateCommentUnlike(User user, Comment comment) {
		Likes like = null;
		try {
			like = likeDao.findByPostAndUserAndCommentId(comment.getPost(), user, comment.getCommentId());
			if (like == null) {
				return null;
			}
			like.setRecordStatus(false);
			like = likeDao.save(like);
		} catch (HibernateException e) {
			logger.error("DB:error :updateCommentUnlike", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null :updateCommentUnlike", e.getMessage());
		} catch (Exception e) {
			logger.error("Error :updateCommentUnlike", e.getMessage());
		}
		return like;

	}

	@Override
	public MedsolResponse<Likes> createLike(long postId, long userId) {
		MedsolResponse<Likes> response = new MedsolResponse<>(true, 200, Constants.OK, null);
		try {
			Post post = postDao.findByPostId(postId);
			if (post == null)
				throw new PostNotFoundException(Constants.RESOURCE_NOT_FOUND);
			User user = userDao.findByUserId(userId);
			if (user == null)
				throw new UsernameNotFoundException(Constants.USER_NOT_FOUND);
			Likes like = likeService.createNewLike(user, post);
			response = new MedsolResponse<>(true, 200, Constants.OK, like);
		} catch (HibernateException e) {
			logger.error("DB:error :createLike", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null :createLike", e.getMessage());
		} catch (Exception e) {
			logger.error("Error :createLike", e.getMessage());
		}
		return response;
	}

	@Override
	public MedsolResponse<Likes> postUnlike(long postId, long userId) {
		MedsolResponse<Likes> response = new MedsolResponse<>(true, 200, Constants.OK, null);
		try {
			Post post = postDao.findByPostId(postId);
			if (post == null)
				throw new PostNotFoundException(Constants.RESOURCE_NOT_FOUND);
			User user = userDao.findByUserId(userId);
			if (user == null)
				throw new UsernameNotFoundException(Constants.USER_NOT_FOUND);
			Likes like = likeService.updateUnlike(user, post);
			response = new MedsolResponse<>(true, 200, Constants.OK, like);
		} catch (HibernateException e) {
			logger.error("DB:error :postUnlike", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null :postUnlike", e.getMessage());
		} catch (Exception e) {
			logger.error("Error :postUnlike", e.getMessage());
		}
		return response;
	}

	@Override
	public MedsolResponse<Likes> createCmtLike(long commentId, long userId) {
		MedsolResponse<Likes> response = new MedsolResponse<>(true, 200, Constants.OK, null);
		try {
			Comment comment = commentDao.findByCommentId(commentId);
			if (comment == null)
				throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
			User user = userDao.findByUserId(userId);
			if (user == null)
				throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
			response = new MedsolResponse<>(true, 200, Constants.OK, likeService.createNewCommentLike(comment, user));
		} catch (HibernateException e) {
			logger.error("DB:error :createCmtLike", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null :createCmtLike", e.getMessage());
		} catch (Exception e) {
			logger.error("Error :createCmtLike", e.getMessage());
		}
		return response;
	}

	@Override
	public MedsolResponse<Likes> unlikeCmt(long commentId, long userId) {
		MedsolResponse<Likes> response = new MedsolResponse<>(false, 200, Constants.OK, null);
		try {
			Comment comment = commentDao.findByCommentId(commentId);
			if (comment == null)
				throw new PostNotFoundException(Constants.RESOURCE_NOT_FOUND);
			User user = userDao.findByUserId(userId);
			if (user == null)
				throw new UsernameNotFoundException(Constants.USER_NOT_FOUND);
			Likes updateCommentUnlike = likeService.updateCommentUnlike(user, comment);
			response = new MedsolResponse<>(true, 200, Constants.OK, updateCommentUnlike);
		} catch (HibernateException e) {
			logger.error("DB:error :postUnlike", e.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null :postUnlike", e.getMessage());
		} catch (Exception e) {
			logger.error("Error :postUnlike", e.getMessage());
		}
		return response;
	}

}
