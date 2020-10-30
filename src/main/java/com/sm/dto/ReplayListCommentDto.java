package com.sm.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReplayListCommentDto {
	private long userId;
	private String userName;
	private long commentId;
	private String commentedText;
	private Timestamp commentedTime;
	private long likeCount;
	private boolean isLike;
}
