package com.finance_app.finance_app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;

@Controller
public class HomeController {

	@Autowired
	private ApplicationContext context;

	
	@FXML
    private Button buttonRegister;
	
	@FXML
    private Button buttonLogin;
	
	@FXML
    private Button buttonHelp;

    @FXML
    public void onClickRegister(ActionEvent event) {
        this.loadView("/fxml/register-view.fxml", event);
    }
    
    @FXML
    public void onClickLogin(ActionEvent event) {
    	this.loadView("/fxml/login-view.fxml", event);
    }
    
    @FXML
    public void onClickHelp(ActionEvent event) {
    	this.loadView("/fxml/help-view.fxml", event);
    }
    
    @FXML
    private void loadView(String fxml, ActionEvent event) {
        try {
            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxml));

            // Aquí Spring inyecta automáticamente el controlador desde el contexto de Spring
            loader.setControllerFactory(context::getBean);

            // Cargar la vista
            Parent root = loader.load();

            // Obtener el Stage actual (la ventana)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // Usamos `event.getSource()` para obtener el origen del evento (el botón)

            // Crear la nueva escena y mostrarla
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
