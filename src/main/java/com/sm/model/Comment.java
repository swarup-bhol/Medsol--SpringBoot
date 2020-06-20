package com.sm.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long commentId;
	private String comment;
	@UpdateTimestamp
	private Timestamp createdTime;
	
	private long reCommentId;
	
	@JsonIgnore
	@ManyToOne 
	@JoinColumn(name = "postId")
	private Post post;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	
	public long getReCommentId() {
		return reCommentId;
	}

	public void setReCommentId(long reCommentId) {
		this.reCommentId = reCommentId;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
