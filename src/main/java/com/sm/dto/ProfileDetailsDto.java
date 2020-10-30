package com.sm.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProfileDetailsDto {

	private long userId;
	private String email;
	private String fullName;
	private String profession;
	private String specialization;
	private String subSpecialization;
	private String grade;
	private String institute;
	private String profileId;
	private String docId;
	private String mobile;
	private Date dob;
	private boolean isMobileVerrified;
	private boolean isEmailVerrified;
	private boolean isDocUploaded;
	private long follower;
	private long following;


}
