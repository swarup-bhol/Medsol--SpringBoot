package com.sm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sm.model.Comment;
import com.sm.model.Post;
import com.sm.model.User;

@Repository
public interface CommentDao extends JpaRepository<Comment, Long> {
	Comment findByCommentId(long commentId);
	List<Comment> findByPostAndReCommentId(Post post,long commentId);
	List<Comment> findByReCommentId(long commentId);
	List<Comment> findByUser(User user);
	long  countByPost(Post post);
}
