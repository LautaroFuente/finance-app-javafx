package com.finance_app.finance_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import com.finance_app.finance_app.service.UserService;
import com.finance_app.finance_app.validation.Validator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Controller
public class RegisterController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationContext context;
	
	@FXML
    private TextField nameField;  
	
	@FXML
    private TextField errorNameField; 
	
    @FXML
    private TextField emailField;
    
    @FXML
    private TextField errorEmailField; 
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField errorPasswordField;
    
    @FXML
    private Button buttonSubmit;    
	
	@FXML
	public void onSubmit() {
		if(!Validator.validateUsername(this.nameField.getText())) {
			this.errorNameField.setText("El nombre no es válido");
			return;
		}
		
		if(!Validator.validateEmail(this.emailField.getText())) {
			this.errorEmailField.setText("El email no es válido");
			return;
		}
		
		if(!Validator.validatePassword(this.passwordField.getText())) {
			this.errorPasswordField.setText("La contraseña no es válida");
			return;
		}
		
		String responseRegister = this.userService.addUser(this.nameField.getText(), this.emailField.getText(), this.passwordField.getText());
		
		if(responseRegister == "Usuario registrado exitosamente") {
			
		}else {
			
		}
	}
}
