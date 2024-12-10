package com.finance_app.finance_app.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.finance_app.finance_app.DTO.TransactionForListDTO;
import com.finance_app.finance_app.service.TransactionListDataService;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class MovementListController {
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private TransactionListDataService transactionListDataService;

	@FXML
	private TableView<TransactionForListDTO> transactionList;
	
	@FXML
	private TableColumn<TransactionForListDTO, String> transactionType;
	
	@FXML
	private TableColumn<TransactionForListDTO, 	Double> transactionAmount;
	
	@FXML
	private TableColumn<TransactionForListDTO, String> transactionCategory;
	
	@FXML
	private TableColumn<TransactionForListDTO, String> transactionDate;
	
	@FXML
	private TableColumn<TransactionForListDTO, String> transactionView;
	
	public void initialize() {
		
		// Configurar lista transacciones
		this.initList();
		
	}
	
	private void initList() {
		// Configuramos los datos de las columnas para mostrar los campos necesarios en cada celda
		transactionType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
		transactionAmount.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getAmount()).asObject());
		transactionCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNameCategory()));
		transactionDate.setCellValueFactory(cellData -> {
		    LocalDateTime date = cellData.getValue().getDate();  // Obtienes el LocalDateTime
		    if (date != null) {
		        // Formateas la fecha en el formato que prefieras
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); // ejemplo de formato
		        return new SimpleStringProperty(date.format(formatter));  // Convierte la fecha formateada a String
		    } else {
		        return new SimpleStringProperty("");  // Si la fecha es nula, mostramos una cadena vacía
		    }
		});
		transactionView.setCellFactory(param -> new TableCell<TransactionForListDTO, String>() {
		    @Override
		    protected void updateItem(String item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty) {
		            setGraphic(null);  // Si la celda está vacía, no mostramos nada
		        } else {
		            // Crear un botón en cada celda
		            Button btnAction = new Button("Ver");
		            
		            // Asignar una acción al botón
		            btnAction.setOnAction(event -> viewSelectedTransaction(event, getTableView().getItems().get(getIndex())));

		            setGraphic(btnAction);  // Establecer el botón como el contenido de la celda
		        }
		    }
		});

		
		// Cargar datos en la lista
		transactionList.setItems(FXCollections.observableArrayList(this.transactionListDataService.getTransactionsForListWallet().stream().limit(7).collect(Collectors.toList())));
	}
	
	private void viewSelectedTransaction(ActionEvent event, TransactionForListDTO transaction) {
		// Intentamos cargar la nueva vista pasandole la transaccion a visualizar
		try {
			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/movement-detail-view.fxml"));
            loader.setControllerFactory(context::getBean);

            Parent root = loader.load();
            
            MovementDetailController controller = loader.getController();
            controller.setTransaction(transaction);

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
