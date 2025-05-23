package com.finance_app.finance_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.finance_app.finance_app.entities.User;
import com.finance_app.finance_app.service.LoadNewViewService;
import com.finance_app.finance_app.service.UserService;
import com.finance_app.finance_app.utils.SessionManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;

@Controller
public class ConfigurationController {

	@Autowired
	private LoadNewViewService loadNewViewService;
	
	@Autowired
	private UserService userService;
	
	@FXML
	private VBox root;
	
	@FXML
	private Button buttonBack;
	
	@FXML
	private RadioButton buttonRadio1;
	
	@FXML
	private RadioButton buttonRadio2;
	
	@FXML
	private RadioButton buttonRadio3;
	
	@FXML
	private RadioButton buttonRadio4;
	
	@FXML
	private Button buttonApply;
	
	@FXML
	private ToggleGroup toggleGroup;
	
	@FXML
	private Button deleteAccount;
	
	public void initialize() {
        // Crear el ToggleGroup
        this.toggleGroup = new ToggleGroup();

        // Asignar el ToggleGroup a cada RadioButton
        buttonRadio1.setToggleGroup(toggleGroup);
        buttonRadio2.setToggleGroup(toggleGroup);
        buttonRadio3.setToggleGroup(toggleGroup);
        buttonRadio4.setToggleGroup(toggleGroup);
        
        //Marcar el RadioButton correspondiente a la resolucion actual
        // Esperar a que la escena esté lista
        javafx.application.Platform.runLater(() -> {
            Scene scene = root.getScene();
            Stage stage = (Stage) scene.getWindow();
            checkRadioButton(stage.getWidth());
        });
    }
	
	//segun el ancho recibido marcar el RadioButton correspondiente
	private void checkRadioButton(double width) {
		//convertir width a entero
		int widthInt = (int) width;
		switch(widthInt) {
			case 800:
				this.buttonRadio1.setSelected(true);
				break;
			case 1024:
				this.buttonRadio2.setSelected(true);
				break;
			case 1280:
				this.buttonRadio3.setSelected(true);
				break;
			case 1920:
				this.buttonRadio4.setSelected(true);
				break;
		}
	}
	
	public void applyChanges(ActionEvent event) {
		// Verificamos cuál RadioButton está seleccionado
        RadioButton selectedRadioButton = (RadioButton) this.toggleGroup.getSelectedToggle();

        if (selectedRadioButton != null) {
            // Obtener el texto del RadioButton seleccionado
            String resolution = selectedRadioButton.getText();
            System.out.println("Resolución seleccionada: " + resolution);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // Lógica para cambiar la resolución en base a la selección
            switch (resolution) {
                case "Resolución 800x600":
                	stage.setWidth(800);
                    stage.setHeight(600);
                    break;
                case "Resolución 1024x768":
                	stage.setWidth(1024);
                    stage.setHeight(768);
                    break;
                case "Resolución 1280x1024":
                	stage.setWidth(1280);
                    stage.setHeight(1024);
                    break;
                case "Resolución 1920x1080":
                	stage.setWidth(1920);
                    stage.setHeight(1080);
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("No se ha seleccionado ninguna resolución.");
        }
	}
	
	public void deleteAccountConfirm(ActionEvent event) {
		User userToDelete = SessionManager.getInstance().getUser();
		SessionManager.getInstance().logout();
		this.userService.deleteUser(userToDelete);
		this.loadNewViewService.loadNewView(event, "/fxml/home-view.fxml");
	}
	
	public void goBack(ActionEvent event) {
		this.loadNewViewService.loadNewView(event, "/fxml/wallet-view.fxml");
	}
}
