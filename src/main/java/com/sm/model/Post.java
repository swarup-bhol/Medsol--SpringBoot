package com.sm.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long postId;

	@Lob
	private String postContent;

	private String postImgPath;

	private String postVideoPath;
	
	private String postImgId;

	@CreationTimestamp
	private Timestamp postUploadTime;

	@UpdateTimestamp
	private Timestamp postUpdatedTime;

	@ManyToOne
	@JoinColumn(name = "userId", nullable = false)
	@JsonIgnore
	private User user;

	private boolean recordStatus;

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public String getPostImgPath() {
		return postImgPath;
	}

	public void setPostImgPath(String postImgPath) {
		this.postImgPath = postImgPath;
	}

	public String getPostVideoPath() {
		return postVideoPath;
	}

	public void setPostVideoPath(String postVideoPath) {
		this.postVideoPath = postVideoPath;
	}

	public Timestamp getPostUploadTime() {
		return postUploadTime;
	}

	public void setPostUploadTime(Timestamp postUploadTime) {
		this.postUploadTime = postUploadTime;
	}

	public Timestamp getPostUpdatedTime() {
		return postUpdatedTime;
	}

	public void setPostUpdatedTime(Timestamp postUpdatedTime) {
		this.postUpdatedTime = postUpdatedTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(boolean recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getPostImgId() {
		return postImgId;
	}

	public void setPostImgId(String postImgId) {
		this.postImgId = postImgId;
	}
	
	

}
