package com.sm.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sm.dao.NotificationDao;
import com.sm.model.Notification;
import com.sm.model.Post;
import com.sm.model.User;
import com.sm.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
	public static final String COMMENT = "comment";
	public static final String LIKE = "like";

	private static Logger log = LoggerFactory.getLogger(NotificationService.class);

	@Autowired
	NotificationDao notificaiondao;

	@Autowired
	private SimpMessagingTemplate template;

	@Override
	public List<Notification> getNotification(long userId) {
		List<Notification> findNotificationByUserId = new ArrayList<Notification>();
		try {
			findNotificationByUserId = notificaiondao.findNotificationByUserId(userId);
		} catch (HibernateException e) {
			log.error("DB Error: getNotification", e.getMessage());
		} catch (Exception e) {
			log.error("Error: getNotification", e.getMessage());
		}
		return findNotificationByUserId;
	}

	@Async
	private void updatenotification(List<Notification> findNotificationByUserId) {
		Iterator<Notification> iterator = findNotificationByUserId.iterator();
		while (iterator.hasNext()) {
			log.info("Async is executing");
			iterator.next().setLastViewedTime(new Timestamp(System.currentTimeMillis()));
		}
		notificaiondao.saveAll(findNotificationByUserId);

	}

	@Override
	public Notification createNotification(Post post, User user, String type) {

		Notification notification1 = null;
		try {
			notification1 = notificaiondao.findByPostIdAndNotificatonType(post.getPostId(), type);
			if (notification1 == null) {

				Notification notification = Notification.builder().notificatonType(type).recordCount(1)
						.postId(post.getPostId()).userId(post.getUser().getUserId())
						.userName(post.getUser().getFullName())
						.lastViewedTime(new Timestamp(System.currentTimeMillis() - 1)).recordStatus(true).build();

				notification1 = notificaiondao.save(notification);
			} else {
				notification1.setRecordCount(notification1.getRecordCount() + 1);
				notification1.setUserName(user.getFullName());
				notification1 = notificaiondao.save(notification1);
				sendNotification(post);
			}
			sendNotification(post);
		} catch (HibernateException e) {
			log.error("DB Error: createNotification", e.getMessage());
		} catch (NullPointerException e) {
			log.error("Null : createNotification", e.getMessage());
		} catch (Exception e) {
			log.error("Error: createNotification", e.getMessage());
		}
		return notification1;
	}

	@Override
	public List<Notification> getOldNotification(long userId, int pageNo) {
		List<Notification> notifications = new ArrayList<Notification>();
		try {
			notifications = notificaiondao.findByUserIdOrderByRecordCreatedTimeDesc(userId, PageRequest.of(pageNo, 10));
		} catch (HibernateException e) {
			log.error("DB Error: createNotification", e.getMessage());
		} catch (Exception e) {
			log.error("Error: createNotification", e.getMessage());
		}
		return notifications;
	}

	private void sendNotification(Post post) {
		long userId = post.getUser().getUserId();
		String user = String.valueOf(userId);
		List<Notification> notification = getNotification(userId);
		template.convertAndSendToUser(user, "/reply", notification);
	}

}
