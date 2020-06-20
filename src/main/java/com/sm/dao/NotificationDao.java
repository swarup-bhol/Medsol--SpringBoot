package com.sm.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sm.model.Notification;


@Repository
public interface NotificationDao extends JpaRepository<Notification, Long>{
   Notification findByPostIdAndNotificatonType(long postId,String type); 
   @Query("SELECT n FROM Notification n where n.recordCreatedTime > n.lastViewedTime and n.userId = ?1")
   List<Notification>  findNotificationByUserId(long usreId);
   
   List<Notification> findByUserIdOrderByRecordCreatedTimeDesc(long userId, Pageable page);
}

