package com.sm.service.impl;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sm.dao.NotificationDao;
import com.sm.model.Notification;
import com.sm.model.Post;
import com.sm.model.User;
import com.sm.service.NotificationService;


@Service
public class NotificationServiceImpl implements NotificationService{
	
	private static Logger log = LoggerFactory.getLogger(NotificationService.class);
	@Autowired
	NotificationDao notificaiondao;
	
	public static final String COMMENT ="comment";
	public static final String LIKE = "like";

	@Override
	public List<Notification> getNotification(long userId) {
	   List<Notification> findNotificationByUserId = notificaiondao.findNotificationByUserId(userId);
	   if(! findNotificationByUserId.isEmpty()) {
		   updatenotification(findNotificationByUserId);
	   }   
		return findNotificationByUserId;
	}

	@Async
	private void updatenotification(List<Notification> findNotificationByUserId) {
		Iterator<Notification> iterator = findNotificationByUserId.iterator();
		while(iterator.hasNext()) {
			log.info("Async is executing");
			iterator.next().setLastViewedTime(new  Timestamp(System.currentTimeMillis()));
		}
		notificaiondao.saveAll(findNotificationByUserId);
		
	}

	@Override
	public Notification createNotification(Post post, User user, String type) {
		
		Notification notification1 = notificaiondao.findByPostIdAndNotificatonType(post.getPostId(),type);
		if(notification1  == null) {
			Notification notification  = new Notification();
			notification.setNotificatonType(type);
			notification.setRecordCount(1);
			notification.setPostId(post.getPostId());
			notification.setUserId(post.getUser().getUserId());
			notification.setUserName(user.getFullName());
			notification.setLastViewedTime(new Timestamp(System.currentTimeMillis()-1));
			notification.setRecordStatus(true);
			return notificaiondao.save(notification);
		}else {
			notification1.setRecordCount(notification1.getRecordCount()+1);
			notification1.setUserName(user.getFullName());
			return notificaiondao.save(notification1);
		}
	}

	@Override
	public List<Notification> getOldNotification(long userId, int pageNo) {
		return notificaiondao.findByUserIdOrderByRecordCreatedTimeDesc(userId, PageRequest.of(pageNo, 10));

	}

}
