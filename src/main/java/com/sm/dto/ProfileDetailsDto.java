package com.sm.dto;

import java.sql.Date;


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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getSubSpecialization() {
		return subSpecialization;
	}

	public void setSubSpecialization(String subSpecialization) {
		this.subSpecialization = subSpecialization;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public boolean isMobileVerrified() {
		return isMobileVerrified;
	}

	public void setMobileVerrified(boolean isMobileVerrified) {
		this.isMobileVerrified = isMobileVerrified;
	}

	public boolean isEmailVerrified() {
		return isEmailVerrified;
	}

	public void setEmailVerrified(boolean isEmailVerrified) {
		this.isEmailVerrified = isEmailVerrified;
	}

	public boolean isDocUploaded() {
		return isDocUploaded;
	}

	public void setDocUploaded(boolean isDocUploaded) {
		this.isDocUploaded = isDocUploaded;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public long getFollower() {
		return follower;
	}

	public void setFollower(long follower) {
		this.follower = follower;
	}

	public long getFollowing() {
		return following;
	}

	public void setFollowing(long following) {
		this.following = following;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

}
