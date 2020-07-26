package com.sm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PostType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long postTypeId;
	@ManyToOne
	@JoinColumn(name = "postId")
	private Post post;
	@ManyToOne
	@JoinColumn(name = "specId")
	private Specialization specialization;

	public long getPostTypeId() {
		return postTypeId;
	}

	public void setPostTypeId(long postTypeId) {
		this.postTypeId = postTypeId;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public Specialization getSpecialization() {
		return specialization;
	}

	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}

}
