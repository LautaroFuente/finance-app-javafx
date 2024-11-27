package com.finance_app.finance_app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class JavaFXSpringBootApp extends Application {

    private ConfigurableApplicationContext context;

    public static void main(String[] args) {
        launch(args);  // Inicia JavaFX
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Inicia Spring Boot
        context = new SpringApplicationBuilder(FinanceApp.class)
                .run();

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
        primaryStage.show();
    }

    @Override
    public void stop() {
        // Cierra el contexto de Spring cuando la aplicación JavaFX termine
        context.close();
    }
}

