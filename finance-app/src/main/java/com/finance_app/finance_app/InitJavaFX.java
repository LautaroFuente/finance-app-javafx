package com.finance_app.finance_app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InitJavaFX extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
    public void start(Stage primaryStage) throws IOException {
        // Carga el archivo FXML
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/home-view.fxml"));
        
        // Cargar el archivo FXML
        AnchorPane root = loader.load();

        // Crear la escena y configurar el escenario
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mi App de Finanzas");
        primaryStage.show();
    }

}
