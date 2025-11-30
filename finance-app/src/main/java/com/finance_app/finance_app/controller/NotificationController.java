package com.finance_app.finance_app.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.finance_app.finance_app.entities.Notification;
import com.finance_app.finance_app.entities.User;
import com.finance_app.finance_app.service.LoadNewViewService;
import com.finance_app.finance_app.service.NotificationService;
import com.finance_app.finance_app.utils.SessionManager;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;

@Controller
public class NotificationController {
	
	@Autowired
	private LoadNewViewService goBackService;
	
	@Autowired
	private NotificationService notificationService;

	@FXML
	private ListView<Notification> notificationList;
	
	@FXML
    private Button buttonBack;
	
	@FXML
	private Label messageAboutList;
	
	private int page;
	private final int SIZE = 5;
	
	public void initialize() throws AuthenticationException {
		// Iniciar la paginacion en cero
		this.page = 0;
		
		// Obtener el usuario verificando que esta la sesion activa
		User user = SessionManager.getInstance().getUser();
		
		if(user != null) {
			// Obtener las notificaciones del usuario
			List<Notification> notifications = this.notificationService.getAllNotificationsForUser(user.getId(), this.page, this.SIZE);
			
			// Comprobar si la lista es vacia o nula
			if(notifications != null && !notifications.isEmpty()) {
				// Aumentar paginacion
				this.page++;
				
				// Asignarlas a la lista
				System.out.println("notificaciones");
				System.out.println(notifications);
				System.out.println(notifications.get(0));
				this.notificationList.setItems(FXCollections.observableArrayList(notifications));
			}
			// Si es vacia o nula agregar mensaje de no hay notificaciones
			else {
				this.messageAboutList.setText("No hay notificaciones");
			}
			
			// Obtener la barra de desplazamiento vertical del ListView
		    ScrollBar scrollBar = (ScrollBar) this.notificationList.lookup(".scroll-bar:vertical");

		    // Comprobar si la barra de desplazamiento es nula (por si no se ha creado aún)
		    if (scrollBar != null) {
		        scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
		            // Si el usuario ha llegado al final del scroll
		            if (scrollBar.getValue() == scrollBar.getMax()) {
		                // Cargar más resultados
		                loadMoreNotifications();
		            }
		        });
		    }
		    
		 // --- Cell factory para agregar botón "X" en cada notificación ---
		    notificationList.setCellFactory(listView -> new javafx.scene.control.ListCell<Notification>() {
		        @Override
		        protected void updateItem(Notification notification, boolean empty) {
		            super.updateItem(notification, empty);

		            if (empty || notification == null) {
		                setText(null);
		                setGraphic(null);
		                return;
		            }

		            // Layout de la notificación
		            javafx.scene.layout.HBox container = new javafx.scene.layout.HBox();
		            container.setSpacing(10);

		            Label msgLabel = new Label(notification.getMessage());
		            Label dateLabel = new Label(notification.getDate().toString());

		            Button deleteBtn = new Button("X");
		            deleteBtn.setOnAction(e -> deleteNotification(notification));

		            container.getChildren().addAll(msgLabel, dateLabel, deleteBtn);
		            setGraphic(container);
		        }
		    });

		}
		// Si no existe lanzar excepcion
		else {
			throw new AuthenticationException("El usuario no está autenticado.");
		}
	}
	
	private void loadMoreNotifications() {
		List<Notification> moreNotifications = this.notificationService.getAllNotificationsForUser(SessionManager.getInstance().getUser().getId(), page, SIZE);
		
		// Comprobar si la lista es vacia o nula
		if(moreNotifications != null && !moreNotifications.isEmpty()) {
			// Aumentar paginacion
			this.page++;
			
			// Agregar nuevos datos a la lista
			this.notificationList.getItems().addAll(moreNotifications);
		}
		// Si lista es vacia o nula agregar mensaje de no hay mas notificaciones
		else {
			this.messageAboutList.setText("No hay mas notificaciones");
		}
	}
	
	private void deleteNotification(Notification notification) {
	    // 1. Borrar de la BD
	    notificationService.delete(notification.getId());

	    // 2. Borrar de la vista
	    notificationList.getItems().remove(notification);
	}

	
	public void goBack(ActionEvent event) {
		this.goBackService.loadNewView(event, "/fxml/wallet-view.fxml");
	}
}
