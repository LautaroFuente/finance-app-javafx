package com.finance_app.finance_app.controller;

import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;

import com.finance_app.finance_app.entities.Notification;
import com.finance_app.finance_app.entities.User;
import com.finance_app.finance_app.service.GoBackService;
import com.finance_app.finance_app.service.NotificationService;
import com.finance_app.finance_app.utils.SessionManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class NotificationController {
	
	@Autowired
	private GoBackService goBackService;
	
	@Autowired
	private NotificationService notificationService;

	@FXML
	private ListView notificationList;
	
	@FXML
    private Button buttonBack;
	
	public void initialize() throws AuthenticationException {
		// Obtener el usuario verificando que esta la sesion activa
		User user = SessionManager.getInstance().getUser();
		
		if(user != null) {
			// Obtener las notificaciones del usuario
			List<Notification> notifications = this.notificationService.getAllNotificationsForUser(user.getId());
			
			// Asignarlas a la lista
		}
		// Si no existe lanzar excepcion
		else {
			throw new AuthenticationException("El usuario no está autenticado.");
		}
	}
	
	public void goBack(ActionEvent event) {
		this.goBackService.goBack(event, "/fxml/wallet-view.fxml");
	}
}
