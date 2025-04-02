package com.finance_app.finance_app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.finance_app.finance_app.service.LoadNewViewService;
import com.finance_app.finance_app.service.UserService;
import com.finance_app.finance_app.service.WalletService;
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
	private LoadNewViewService loadNewViewService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WalletService walletService;

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
	@Transactional
	public void onSubmit(ActionEvent event) {
		
		cleanErrorFields();
		boolean hasErrors = false;

		if (!Validator.validateUsername(this.nameField.getText())) {
		    this.errorNameField.setText("El nombre no es válido, no puede estar vacío");
		    hasErrors = true;
		}

		if (!Validator.validateEmail(this.emailField.getText())) {
		    this.errorEmailField.setText("El email no es válido");
		    hasErrors = true;
		}

		if (!Validator.validatePassword(this.passwordField.getText())) {
		    this.errorPasswordField.setText("La contraseña no debe ser vacía y debe tener mas de 6 caracteres");
		    hasErrors = true;
		}

		if (hasErrors) {
		    return;  // No seguir ejecutando si hay errores
		}
		
		cleanFields();
		
		String responseRegisterUser = this.userService.addUser(this.nameField.getText(), this.emailField.getText(), this.passwordField.getText());
		
		if("Usuario registrado exitosamente".equals(responseRegisterUser)) {
			this.responseMessage.setText("Usuario registrado exitosamente");
			
			// Crear billetera y asociarla al nuevo usuario
			try {
                this.walletService.addWallet(this.userService.getOneUser(this.emailField.getText()));
            } catch (Exception e) {
                // Si ocurre un error al crear la billetera, deshacer la transacción de usuario
                throw new RuntimeException("Error al crear la billetera para el usuario", e);
                // TODO: controlar error para no dejar colgada la excepcion ni cerrar la app
            }
			
			this.loadNewViewService.loadNewView(event, "/fxml/home-view.fxml");

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
	
	public void loadNewView(ActionEvent event) {
		this.loadNewViewService.loadNewView(event, "/fxml/home-view.fxml");
	}
}
