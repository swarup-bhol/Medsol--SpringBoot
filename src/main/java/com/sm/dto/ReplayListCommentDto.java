package com.sm.dto;

import java.sql.Timestamp;

public class ReplayListCommentDto {
	private long userId;
	private String userName;
	private long commentId;
	private String commentedText;
	private Timestamp commentedTime;
	private long likeCount;
	private boolean isLike;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public String getCommentedText() {
		return commentedText;
	}

	public void setCommentedText(String commentedText) {
		this.commentedText = commentedText;
	}

	public Timestamp getCommentedTime() {
		return commentedTime;
	}

	public void setCommentedTime(Timestamp commentedTime) {
		this.commentedTime = commentedTime;
	}

	public long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(long likeCount) {
		this.likeCount = likeCount;
	}

	public boolean isLike() {
		return isLike;
	}

	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}

}
