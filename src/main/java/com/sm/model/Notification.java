package com.sm.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity @Builder @Getter @Setter
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

}
