package com.finance_app.finance_app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import com.finance_app.finance_app.entities.User;
import com.finance_app.finance_app.service.AuthService;
import com.finance_app.finance_app.service.LoadNewViewService;
import com.finance_app.finance_app.service.UserService;
import com.finance_app.finance_app.utils.SessionManager;
import com.finance_app.finance_app.validation.Validator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoadNewViewService goBackService;
	
	@Autowired
	private AuthService authService;

	@Autowired
	private ApplicationContext context;
	
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
			cleanFields();
			return;
		}
		
		String responseLogin = this.authService.authUser(this.emailField.getText(), this.passwordField.getText());
		
		if("Usuario logueado".equals(responseLogin)) {
			this.responseMessage.setText(responseLogin);
			
			try {
				// Crear la instancia del nuevo usuario que se loguea
				User user = this.userService.getOneUser(this.emailField.getText());
				SessionManager.getInstance().login(user);
				
				// Cargar la vista
				FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(getClass().getResource("/fxml/wallet-view.fxml"));
	            loader.setControllerFactory(context::getBean);

	            Parent root = loader.load();

	            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // Usamos `event.getSource()` para obtener el origen del evento (el botón)
	            
	            double currentWidth = stage.getWidth();
	            double currentHeight = stage.getHeight();

	            // Crear la nueva escena y mostrarla
	            Scene scene = new Scene(root, currentWidth, currentHeight);
	            stage.setScene(scene);
	            stage.show();
	        } catch (IOException e) {
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
		this.goBackService.loadNewView(event, "/fxml/home-view.fxml");
	}
}
