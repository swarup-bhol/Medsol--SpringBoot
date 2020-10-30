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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Builder @Getter @Setter @NoArgsConstructor @AllArgsConstructor
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

}
