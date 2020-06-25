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
public class Likes {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long likeId;
	
	@UpdateTimestamp
	private Timestamp likeTime;
	
	@JsonIgnore
	@ManyToOne  
	@JoinColumn(name = "postId")
	private Post post;
	
	@JsonIgnore
	@ManyToOne 
	@JoinColumn(name = "userId")
	private User user;
	
	private long commentId;
	
	private boolean recordStatus;

	public long getLikeId() {
		return likeId;
	}

	public void setLikeId(long likeId) {
		this.likeId = likeId;
	}


	public Timestamp getLikeTime() {
		return likeTime;
	}

	public void setLikeTime(Timestamp likeTime) {
		this.likeTime = likeTime;
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
	
	

	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public boolean isRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(boolean recordStatus) {
		this.recordStatus = recordStatus;
	}



}
