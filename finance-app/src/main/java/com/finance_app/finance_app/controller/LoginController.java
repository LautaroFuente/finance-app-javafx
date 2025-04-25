package com.finance_app.finance_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.finance_app.finance_app.entities.User;
import com.finance_app.finance_app.service.AuthService;
import com.finance_app.finance_app.service.LoadNewViewService;
import com.finance_app.finance_app.service.UserService;
import com.finance_app.finance_app.utils.SessionManager;
import com.finance_app.finance_app.validation.Validator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoadNewViewService loadNewViewService;
	
	@Autowired
	private AuthService authService;
	
    @FXML
    private TextField emailField;
    
    @FXML
    private Label errorEmailField; 
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Label errorPasswordField;
    
    @FXML
    private Button buttonSubmit;  
    
    @FXML
    private Label responseMessage;
    
    @FXML
    private Button buttonBack;
    
    @FXML
	public void onSubmit(ActionEvent event) {
		
    	cleanErrorFields();
		if(!Validator.validateEmail(this.emailField.getText())) {
			this.errorEmailField.setText("El email no tiene un formato correcto");
			return;
		}
		
		String responseLogin = this.authService.authUser(this.emailField.getText(), this.passwordField.getText());
		
		if("Usuario logueado".equals(responseLogin)) {
			this.responseMessage.getStyleClass().removeAll("errorMessage");
			if (!responseMessage.getStyleClass().contains("infoMessage")) {
			    responseMessage.getStyleClass().add("infoMessage");
			}
			this.responseMessage.setText(responseLogin);
			
			try {
				// Crear la instancia del nuevo usuario que se loguea
				User user = this.userService.getOneUser(this.emailField.getText());
				SessionManager.getInstance().login(user);
				cleanFields();
				// Cargar la vista
				this.loadNewViewService.loadNewView(event, "/fxml/wallet-view.fxml");
				} catch (Exception e) {
	        	Alert alert = new Alert(Alert.AlertType.ERROR, "Error al iniciar sesion del usuario, por intente nuevamente.", ButtonType.OK);
	        	alert.showAndWait();
	            e.printStackTrace();
	        }
			

		}else {
			this.responseMessage.setText(responseLogin);
		}
	}
	
	private void cleanFields() {
		this.emailField.setText("");
		this.passwordField.setText("");
	}
	
	private void cleanErrorFields() {
		this.errorEmailField.setText("");
		this.errorPasswordField.setText("");
	}
	
	public void goBack(ActionEvent event) {
		this.loadNewViewService.loadNewView(event, "/fxml/home-view.fxml");
	}
}
