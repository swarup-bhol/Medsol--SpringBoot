package com.sm.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sm.model.Notification;
import com.sm.service.NotificationService;
import com.sm.util.MedsolResponse;
import com.sm.util.Constants;

@RestController
public class NotificationController {

	@Autowired
	NotificationService notificationservice;
	
	@Autowired
    private SimpMessagingTemplate template;

    
    @GetMapping("/sendNotification")
    public String sendNotification() {
    	template.convertAndSendToUser("swarup", "/reply", "swarup");
    	return "Notification send succefully";
    }
	
    
    /**
     * @author swarupb
     *
     * 
     * @param userId
     * @return
     */
	@GetMapping("/api/medsol/notification/new/{userId}")
	public MedsolResponse<Notification> getNotification(@PathVariable long userId){
		return new MedsolResponse<Notification>(true ,200,Constants.OK,notificationservice.getNotification(userId));
	}
	
	
	/**
	 * @author swarupb
	 * 
	 * @param userId
	 * @param pageNo
	 * @return
	 */
	@GetMapping("/api/medsol/notification/{userId}/{pageNo}")
	public MedsolResponse<Notification> getInitialNotification(@PathVariable long userId,@PathVariable int pageNo){
		return new MedsolResponse<Notification>(true ,200,Constants.OK,notificationservice.getOldNotification(userId, pageNo));
	}
}
