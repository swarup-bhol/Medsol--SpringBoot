package com.sm.service;

import org.springframework.stereotype.Service;

import com.sm.model.Comment;
import com.sm.model.Likes;
import com.sm.model.Post;
import com.sm.model.User;

@Service
public interface LikeService {
	
	public Likes createNewLike(User user, Post post);

	public Likes updateUnlike(User user, Post post);

	public Likes createNewCommentLike(Comment comment, User user);

	public Likes updateCommentUnlike(User user, Comment comment);

}
