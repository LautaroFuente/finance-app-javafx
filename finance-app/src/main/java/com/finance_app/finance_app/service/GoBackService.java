package com.finance_app.finance_app.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Service
public class GoBackService {

	@Autowired
	private ApplicationContext context;
	
	public void goBack(ActionEvent event, String viewRoute) {
		try {
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(viewRoute));
            loader.setControllerFactory(context::getBean);

            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // Usamos `event.getSource()` para obtener el origen del evento (el botón)
            
            double currentWidth = stage.getWidth();
            double currentHeight = stage.getHeight();

            // Crear la nueva escena y mostrarla
            Scene scene = new Scene(root, currentWidth, currentHeight);
            stage.setWidth(currentWidth); 
            stage.setHeight(currentHeight);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
