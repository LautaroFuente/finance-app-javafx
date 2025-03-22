package com.finance_app.finance_app;

import java.io.IOException;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InitJavaFX extends Application{
	
	private ConfigurableApplicationContext context;

	public static void main(String[] args) {
		
		Dotenv dotenv = Dotenv.load();
    	System.setProperty("DB_URL", dotenv.get("DB_URL"));
    	System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
    	System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		launch(args);
	}
	
	@Override
    public void init() throws Exception {
        // Inicia Spring Boot cuando la aplicación JavaFX se inicia
        context = new SpringApplicationBuilder(FinanceAppSpring.class)
                .run();  // Inicializa el contexto de Spring Boot
    }
	
	@Override
    public void start(Stage primaryStage) throws IOException {
		try {
			// Carga el archivo FXML
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/home-view.fxml"));
        
			// Inyecta el controlador de Spring
	        loader.setControllerFactory(context::getBean);
			
			// Cargar el archivo FXML
			AnchorPane root = loader.load();

			// Crear la escena y configurar el escenario
			Scene scene = new Scene(root, 800, 600);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Mi App de Finanzas");
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch(IOException e) {
		    
		    Alert alert = new Alert(Alert.AlertType.ERROR, "Error crítico. La aplicación se cerrará.", ButtonType.OK);
		    alert.showAndWait();
		    
		    e.printStackTrace();
		    Platform.exit(); // Cierra JavaFX
		}
    }
	
	@Override
    public void stop() throws Exception {
        // Cierra el contexto de Spring cuando la aplicación JavaFX termine si no se cerro ya
		if (context != null) {
            context.close();
        }
    }

}
