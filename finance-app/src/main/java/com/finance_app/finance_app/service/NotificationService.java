package com.finance_app.finance_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finance_app.finance_app.entities.Notification;
import com.finance_app.finance_app.repository.NotificationRepository;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;
	
	public List<Notification> getAllNotificationsForUser(Long id){
		return this.notificationRepository.getAllNotificationForUser(id);
	}
}
