package com.finance_app.finance_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.finance_app.finance_app.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{

	@Query("SELECT n FROM Notification n WHERE n.user_id = :user_id")
    List<Notification> getAllNotificationForUser(@Param("user_id") Long user_id);
}
