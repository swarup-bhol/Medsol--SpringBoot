package com.sm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sm.model.Notification;
import com.sm.service.NotificationService;
import com.sm.util.ApiResponse;
import com.sm.util.Constants;

@RestController
public class NotificationController {

	@Autowired
	NotificationService notificationservice;
	
	@GetMapping("/api/medsol/notification/new/{userId}")
	public ApiResponse<Notification> getNotification(@PathVariable long userId){
		return new ApiResponse<Notification>(200,Constants.OK,notificationservice.getNotification(userId));
	}
	
	@GetMapping("/api/medsol/notification/{userId}/{pageNo}")
	public ApiResponse<Notification> getInitialNotification(@PathVariable long userId,@PathVariable int pageNo){
		return new ApiResponse<Notification>(200,Constants.OK,notificationservice.getOldNotification(userId, pageNo));
	}
}
