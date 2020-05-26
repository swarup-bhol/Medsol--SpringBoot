package com.sm.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;


	@NotEmpty
	private String fullName;

	@Column(unique = true)
	@Email
	@NotEmpty
	private String userEmail;


	private String userMobile;

	private Date dateOfBirth;


	@NotEmpty
	private String userPassword;

	@JsonIgnore
	private String mobVerifficationCode;

	@JsonIgnore
	private String emailVerifficationCode;

	private boolean isMobileVerrified;

	private boolean isEmailVerrified;

	@JsonIgnore
	@CreationTimestamp
	private Timestamp profileCreationTime;

	@JsonIgnore
	@UpdateTimestamp
	private Timestamp profileUpdationTime;

	private String instituteName;

	private String profilePicPath;

	private String userDocumentPath;

	@UpdateTimestamp
	private Timestamp docUploadTime;

	private boolean isDocUploaded;

	private long grade;

	private long professionId;

	private long specializationId;

	private long detailsSpecializationId;

	private String profilePicId;

	private String documentId;

	private boolean recordStatus;


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getMobVerifficationCode() {
		return mobVerifficationCode;
	}

	public void setMobVerifficationCode(String mobVerifficationCode) {
		this.mobVerifficationCode = mobVerifficationCode;
	}

	public String getEmailVerifficationCode() {
		return emailVerifficationCode;
	}

	public void setEmailVerifficationCode(String emailVerifficationCode) {
		this.emailVerifficationCode = emailVerifficationCode;
	}

	public boolean isMobileVerrified() {
		return isMobileVerrified;
	}

	public void setMobileVerrified(boolean mobileVerrified) {
		isMobileVerrified = mobileVerrified;
	}

	public boolean isEmailVerrified() {
		return isEmailVerrified;
	}

	public void setEmailVerrified(boolean emailVerrified) {
		isEmailVerrified = emailVerrified;
	}

	public Timestamp getProfileCreationTime() {
		return profileCreationTime;
	}

	public void setProfileCreationTime(Timestamp profileCreationTime) {
		this.profileCreationTime = profileCreationTime;
	}

	public Timestamp getProfileUpdationTime() {
		return profileUpdationTime;
	}

	public void setProfileUpdationTime(Timestamp profileUpdationTime) {
		this.profileUpdationTime = profileUpdationTime;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public String getProfilePicPath() {
		return profilePicPath;
	}

	public void setProfilePicPath(String profilePicPath) {
		this.profilePicPath = profilePicPath;
	}

	public String getUserDocumentPath() {
		return userDocumentPath;
	}

	public void setUserDocumentPath(String userDocumentPath) {
		this.userDocumentPath = userDocumentPath;
	}

	public Timestamp getDocUploadTime() {
		return docUploadTime;
	}

	public void setDocUploadTime(Timestamp docUploadTime) {
		this.docUploadTime = docUploadTime;
	}

	public boolean isDocUploaded() {
		return isDocUploaded;
	}

	public void setDocUploaded(boolean docUploaded) {
		isDocUploaded = docUploaded;
	}

	public long getGrade() {
		return grade;
	}

	public void setGrade(long grade) {
		this.grade = grade;
	}

	public long getProfessionId() {
		return professionId;
	}

	public void setProfessionId(long professionId) {
		this.professionId = professionId;
	}

	public long getSpecializationId() {
		return specializationId;
	}

	public void setSpecializationId(long specializationId) {
		this.specializationId = specializationId;
	}

	public long getDetailsSpecializationId() {
		return detailsSpecializationId;
	}

	public void setDetailsSpecializationId(long detailsSpecializationId) {
		this.detailsSpecializationId = detailsSpecializationId;
	}

	public String getProfilePicId() {
		return profilePicId;
	}

	public void setProfilePicId(String profilePicId) {
		this.profilePicId = profilePicId;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public boolean isRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(boolean recordStatus) {
		this.recordStatus = recordStatus;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
}
