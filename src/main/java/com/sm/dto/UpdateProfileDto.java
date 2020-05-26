package com.sm.dto;

import java.sql.Date;

public class UpdateProfileDto {

	private String email;
	private String name;
	private String institue;
	private String mobileNo;
	private String profession;
	private Date dob;
	private String grade;
	private String specialization;
	private String subspecialization;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInstitue() {
		return institue;
	}

	public void setInstitue(String institue) {
		this.institue = institue;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getSubspecialization() {
		return subspecialization;
	}

	public void setSubspecialization(String subspecialization) {
		this.subspecialization = subspecialization;
	}
	
	

}
