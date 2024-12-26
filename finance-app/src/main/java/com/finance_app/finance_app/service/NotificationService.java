package com.finance_app.finance_app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.finance_app.finance_app.entities.Notification;
import com.finance_app.finance_app.entities.User;
import com.finance_app.finance_app.repository.NotificationRepository;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;
	
	public List<Notification> getAllNotificationsForUser(Long id, int from, int to) {
		Pageable pageable = PageRequest.of(from, to);
		return this.notificationRepository.getAllNotificationForUser(id, pageable).getContent();
	}
	
	public void addNotification(User user, String message) {
		Notification notification = new Notification(user, message, LocalDateTime.now());
		this.notificationRepository.save(notification);
	}
	
	public Integer getNumberOfNotificationFromUser(Long userId) {
		return this.getNumberOfNotificationFromUser(userId);
	}
}
