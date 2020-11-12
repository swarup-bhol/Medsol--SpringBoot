package com.sm.service.impl;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sm.dao.CommentDao;
import com.sm.dao.PostDao;
import com.sm.dto.CommentDto;
import com.sm.dto.CommentListDto;
import com.sm.dto.ReCommentDto;
import com.sm.dto.ReplayListCommentDto;
import com.sm.exception.ResourceNotFoundException;
import com.sm.model.Comment;
import com.sm.model.Post;
import com.sm.model.User;
import com.sm.service.CommentService;
import com.sm.service.NotificationService;
import com.sm.service.UserService;
import com.sm.util.Constants;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String COMMENTED = "commented";
	@Autowired
	UserService userService;

	@Autowired
	PostDao postDao;

	@Autowired
	CommentDao commentDao;

	@Autowired
	NotificationService notificationService;

	
	/**
	 * @author swarupb
	 * 
	 * 
	 * @purpose creating new comment
	 * @param commentDto
	 * @return Objects
	 */
	@Override
	public CommentListDto createNewComment(CommentDto commentDto) {
		CommentListDto dtos = new CommentListDto();
		try {

			Post post = postDao.findByPostId(commentDto.getPostId());
			if (post == null)
				throw new ResourceNotFoundException("Post Not found");
			User user = userService.findByuserId(commentDto.getUserId());
			if (user == null)
				throw new UsernameNotFoundException(Constants.USER_NOT_FOUND);
			
			Comment comment = Comment.builder().comment(commentDto.getMessage()).user(user).post(post).build();
			Comment cmt = commentDao.save(comment);
			notificationService.createNotification(post, user, COMMENTED);

			dtos = CommentListDto.builder().userId(user.getUserId()).commentedTime(cmt.getCreatedTime())
					.commentedText(cmt.getComment()).commentId(cmt.getCommentId()).userName(user.getFullName())
					.replays(new ArrayList<ReplayListCommentDto>()).build();
		} catch (HibernateException hex) {
			logger.error("DB error: createNewComment", hex.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null: createNewComment", e.getMessage());
		} catch (Exception e) {
			logger.error("error: createNewComment", e.getMessage());
		}
		return dtos;
	}

	/**
	 * @author swarupb
	 * 
	 * @purpose create replay comment
	 * @param commentDto
	 * @return commentList
	 */
	@Override
	public CommentListDto createNewReComment(ReCommentDto reCommentDto) {
		Comment comment = null;
		CommentListDto commentListDto = null;

		try {
			comment = commentDao.findByCommentId(reCommentDto.getCommentId());

			User user = userService.findByuserId(reCommentDto.getUserId());
			if (user == null)
				throw new UsernameNotFoundException(Constants.USER_NOT_FOUND);

			if (comment == null)
				throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);

			Comment reComment = Comment.builder().user(user).post(comment.getPost())
					.comment(reCommentDto.getCommentedText()).reCommentId(reCommentDto.getCommentId()).build();

			comment = commentDao.save(reComment);

			commentListDto = CommentListDto.builder().userId(user.getUserId()).commentedTime(comment.getCreatedTime())
					.commentedText(comment.getComment()).commentId(comment.getCommentId()).userName(user.getFullName())
					.replays(new ArrayList<ReplayListCommentDto>()).build();
			
		} catch (HibernateException hex) {
			logger.error("DB error: createNewReComment", hex.getMessage());
		} catch (NullPointerException e) {
			logger.error("Null: createNewReComment", e.getMessage());
		} catch (Exception e) {
			logger.error("error: createNewReComment", e.getMessage());
		}
		return commentListDto;

	}
//	@Override
//	public CommentListDto createNewReComment(ReCommentDto reCommentDto) {
//		Comment comment= commentDao.findByCommentId(reCommentDto.getCommentId());
//		if(comment == null) throw new ResourceNotFoundException(Constants.RESOURCE_NOT_FOUND);
//		User user = userService.findByuserId(reCommentDto.getUserId());
//		if(user == null) throw new UsernameNotFoundException(Constants.USER_NOT_FOUND);
//		ReComment reComment = new ReComment();
//		reComment.setUser(user);
//		reComment.setComment(comment);
//		reComment.setReComment(reCommentDto.getCommentedText());
//		ReComment saveComment = reCommentDao.save(reComment);
//		CommentListDto dtos =new CommentListDto(); 
//		dtos.setUserId(user.getUserId());
//		dtos.setCommentedTime(saveComment.getCreatedTime());
//		dtos.setCommentedText(saveComment.getReComment());
//		dtos.setCommentId(saveComment.getReCommentId());
//		dtos.setUserName(user.getFullName());
//		dtos.setReplays(new ArrayList<ReplayListCommentDto>());
//		return dtos;
//	}

}
