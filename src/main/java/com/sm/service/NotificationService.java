package com.sm.service;

import java.util.List;

import com.sm.model.Notification;
import com.sm.model.Post;
import com.sm.model.User;

public interface NotificationService {
	
	public Notification createNotification(Post post,User user,String type);
	
	public List<Notification> getNotification(long userId);
	
	
	public List<Notification> getOldNotification(long userId, int pageNo);

}
