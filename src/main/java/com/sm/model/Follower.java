package com.sm.model;

import java.sql.Timestamp;

import javax.persistence.*;

import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class Follower {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long followId;

	private long userId;

	private long followedBy;

	@UpdateTimestamp
	private Timestamp fllowTime;

	private boolean isFollowing;

	public long getFollowId() {
		return followId;
	}

	public void setFollowId(long followId) {
		this.followId = followId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getFollowedBy() {
		return followedBy;
	}

	public void setFollowedBy(long followedBy) {
		this.followedBy = followedBy;
	}

	public Timestamp getFllowTime() {
		return fllowTime;
	}

	public void setFllowTime(Timestamp fllowTime) {
		this.fllowTime = fllowTime;
	}

	public boolean isFollowing() {
		return isFollowing;
	}

	public void setFollowing(boolean isFollowing) {
		this.isFollowing = isFollowing;
	}

}
