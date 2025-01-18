package com.finance_app.finance_app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;

public class JavaFXSpringBootApp extends Application {

    private ConfigurableApplicationContext context;
    
    public static void main(String[] args) {
    	
    	Dotenv dotenv = Dotenv.load();
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
    	
        launch(args);  // Inicia JavaFX
    }

    @Override
    public void init() throws Exception {
        // Inicia Spring Boot cuando la aplicación JavaFX se inicia
        context = new SpringApplicationBuilder(FinanceApp.class)
                .run();  // Inicializa el contexto de Spring Boot
    }
    
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Carga el archivo FXML y el controlador de Spring
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
    }

    @Override
    public void stop() throws Exception {
        // Cierra el contexto de Spring cuando la aplicación JavaFX termine
        context.close();
    }
}

