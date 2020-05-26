package com.sm.dto;

import java.sql.Date;

public class Profile {

	private String email;
	private long grade;
	private String institute;
	private String mobile;
	private String name;
	private long profession;
	private long specialization;
	private long subspecialization;
	private Date dob;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getGrade() {
		return grade;
	}

	public void setGrade(long grade) {
		this.grade = grade;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getProfession() {
		return profession;
	}

	public void setProfession(long profession) {
		this.profession = profession;
	}

	public long getSpecialization() {
		return specialization;
	}

	public void setSpecialization(long specialization) {
		this.specialization = specialization;
	}

	public long getSubspecialization() {
		return subspecialization;
	}

	public void setSubspecialization(long subspecialization) {
		this.subspecialization = subspecialization;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

}
