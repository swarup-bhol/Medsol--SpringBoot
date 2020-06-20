package com.sm.dto;

public class ReCommentDto {

	private long commentId;
	private long userId;
	private String commentedText;

	public long getCommentId() {
		return commentId;
	}

	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getCommentedText() {
		return commentedText;
	}

	public void setCommentedText(String commentedText) {
		this.commentedText = commentedText;
	}

}
