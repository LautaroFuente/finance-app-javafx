package com.finance_app.finance_app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import com.finance_app.finance_app.service.GoBackService;
import com.finance_app.finance_app.service.UserService;
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
public class RegisterController {
	
	@Autowired
	private GoBackService goBackService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationContext context;
	
	@FXML
    private TextField nameField;  
	
	@FXML
    private Label errorNameField; 
	
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
		if(!Validator.validateFields(this.nameField.getText(), this.emailField.getText(), this.passwordField.getText())) {
		
			if(!Validator.validateUsername(this.nameField.getText())) {
				this.errorNameField.setText("El nombre no es válido");
			}
		
			if(!Validator.validateEmail(this.emailField.getText())) {
				this.errorEmailField.setText("El email no es válido");
			}
		
			if(!Validator.validatePassword(this.passwordField.getText())) {
				this.errorPasswordField.setText("La contraseña no es válida");
			}
		
			cleanFields();
			return;
		}
		
		String responseRegister = this.userService.addUser(this.nameField.getText(), this.emailField.getText(), this.passwordField.getText());
		
		if("Usuario registrado exitosamente".equals(responseRegister)) {
			this.responseMessage.setText("Usuario registrado exitosamente");
			
			try {
				FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(getClass().getResource("/fxml/home-view.fxml"));
	            loader.setControllerFactory(context::getBean);

	            Parent root = loader.load();

	            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // Usamos `event.getSource()` para obtener el origen del evento (el botón)

	            // Crear la nueva escena y mostrarla
	            Scene scene = new Scene(root);
	            stage.setScene(scene);
	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			

		}else {
			this.responseMessage.setText("Error con el servidor al agregar usuario");
		}
	}
	
	private void cleanFields() {
		this.nameField.setText("");
		this.emailField.setText("");
		this.passwordField.setText("");
	}
	
	private void cleanErrorFields() {
		this.errorNameField.setText("");
		this.errorEmailField.setText("");
		this.errorPasswordField.setText("");
	}
	
	public void goBack(ActionEvent event) {
		this.goBackService.goBack(event, "/fxml/home-view.fxml");
	}
}
