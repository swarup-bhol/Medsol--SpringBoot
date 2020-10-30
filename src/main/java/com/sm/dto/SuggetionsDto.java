package com.sm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class SuggetionsDto {

	private long userId;
	private String userName;
	private String Profession;
	private String institute;
	private boolean isFollowing;

	
}
