package com.finance_app.finance_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.finance_app.finance_app.service.GoBackService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class ConfigurationController {

	@Autowired
	private GoBackService goBackService;
	
	@Autowired
	private ApplicationContext context;
	
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
	
	public void initialize() {
        // Crear el ToggleGroup
        ToggleGroup toggleGroup = new ToggleGroup();

        // Asignar el ToggleGroup a cada RadioButton
        buttonRadio1.setToggleGroup(toggleGroup);
        buttonRadio2.setToggleGroup(toggleGroup);
        buttonRadio3.setToggleGroup(toggleGroup);
        buttonRadio4.setToggleGroup(toggleGroup);
    }
	
	public void goBack(ActionEvent event) {
		this.goBackService.goBack(event, "/fxml/wallet-view.fxml");
	}
	
	public void applyChanges(ActionEvent event) {
		// Verificamos cuál RadioButton está seleccionado
        RadioButton selectedRadioButton = (RadioButton) this.toggleGroup.getSelectedToggle();

        if (selectedRadioButton != null) {
            // Obtener el texto del RadioButton seleccionado
            String resolution = selectedRadioButton.getText();
            System.out.println("Resolución seleccionada: " + resolution);

            // Lógica para cambiar la resolución en base a la selección
            switch (resolution) {
                case "Resolución 800x600":
                    // Cambiar la resolución a 800x600
                    break;
                case "Resolución 1024x768":
                    // Cambiar la resolución a 1024x768
                    break;
                case "Resolución 1280x1024":
                    // Cambiar la resolución a 1280x1024
                    break;
                case "Resolución 1920x1080":
                    // Cambiar la resolución a 1920x1080
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("No se ha seleccionado ninguna resolución.");
        }
	}
}
