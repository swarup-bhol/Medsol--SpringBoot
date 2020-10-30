package com.sm.dto;

import java.util.List;

import com.sm.model.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
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


}
