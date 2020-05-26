package com.sm.dto;

import java.util.List;

import com.sm.model.Post;

public class PostDto {

	private long userId;
	private long likeCount;
	private long commentCount;
	private boolean isLike;
	private String postMediaId;
	private String Profession;
	private String fullName;
	private String instituteName;
	private Post post;
	private List<CommentListDto> commentLIst;

	public long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(long likeCount) {
		this.likeCount = likeCount;
	}

	public String getProfession() {
		return Profession;
	}

	public void setProfession(String profession) {
		Profession = profession;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	

	public String getPostMediaId() {
		return postMediaId;
	}

	public void setPostMediaId(String postMediaId) {
		this.postMediaId = postMediaId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	
	public List<CommentListDto> getCommentLIst() {
		return commentLIst;
	}

	public void setCommentLIst(List<CommentListDto> commentLIst) {
		this.commentLIst = commentLIst;
	}

	public long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(long commentCount) {
		this.commentCount = commentCount;
	}

	public boolean isLike() {
		return isLike;
	}

	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}

}
