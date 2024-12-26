package com.finance_app.finance_app.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.finance_app.finance_app.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{

	@Query("SELECT n FROM Notification n WHERE n.user.id = :user_id ORDER BY n.date")
    Page<Notification> getAllNotificationForUser(@Param("user_id") Long user_id, Pageable pageable);
	
	@Query("SELECT COUNT(n) FROM Notification n WHERE n.user.id = :user_id")
	Integer getNumberOfNotificationFromUser(@Param("user_id") Long user_id);

}
