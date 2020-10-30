package com.sm.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CommentListDto {

	private long userId;
	private String userName;
	private long commentId;
	private String commentedText;
	private long likeCount;
	private boolean isLike;
	private List<ReplayListCommentDto> replays;
	private Timestamp commentedTime;

}
