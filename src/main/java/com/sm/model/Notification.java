package com.sm.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long notificationId;
	public long userId;
	public long postId;
	public String userName;
	public long recordCount;
	public String notificatonType;
	@UpdateTimestamp
	public Timestamp recordCreatedTime;
	public Timestamp lastViewedTime;
	public boolean recordStatus;

	
	public long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}

	public String getNotificatonType() {
		return notificatonType;
	}

	public void setNotificatonType(String notificatonType) {
		this.notificatonType = notificatonType;
	}

	public Timestamp getRecordCreatedTime() {
		return recordCreatedTime;
	}

	public void setRecordCreatedTime(Timestamp recordCreatedTime) {
		this.recordCreatedTime = recordCreatedTime;
	}

	public Timestamp getLastViewedTime() {
		return lastViewedTime;
	}

	public void setLastViewedTime(Timestamp lastViewedTime) {
		this.lastViewedTime = lastViewedTime;
	}

	public boolean isRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(boolean recordStatus) {
		this.recordStatus = recordStatus;
	}

}
