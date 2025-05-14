package com.finance_app.finance_app.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import com.finance_app.finance_app.DTO.TransactionForListDTO;
import com.finance_app.finance_app.service.LoadNewViewService;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

@Controller
public class MovementListController {
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private LoadNewViewService goBackService;
	
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
	
	@FXML
    private Button buttonBack;
	
	@FXML
	private Button previousButton;
	
	@FXML
	private Button nextButton;
	
	@FXML
	private Label messageAboutTransaction;
	
	private int page;
	private final int SIZE = 5;
	private int totalPages;
	
	public void initialize() {
		// Iniciar paginacion en cero
		this.page = 0;
		
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
		        // Formateas la fecha en el formato a mostrar
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); 
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

		// Obtener lista y verificar que no sea nula o vacia
		List<TransactionForListDTO> result = this.transactionListDataService.getTransactionsForListWallet(this.page, this.SIZE);
		if(result != null && !result.isEmpty()) {
			// Cargar datos en la lista
			transactionList.setItems(FXCollections.observableArrayList(result));
			
			// Actualizar total de paginas
			this.totalPages = this.transactionListDataService.getTotalPagesByLastQuery();
			
		}
		// Si no cumple enviar mensaje de que no hay transacciones
		else {
			this.messageAboutTransaction.setText("No hay transacciones");
		}
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
            controller.initLabels();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // Usamos `event.getSource()` para obtener el origen del evento (el botón)
            
            double currentWidth = stage.getWidth();
            double currentHeight = stage.getHeight();

            // Crear la nueva escena y mostrarla
            Scene scene = new Scene(root, currentWidth, currentHeight);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
		
	 private void loadPage() {
		 // Obtener lista y verificar que no sea nula o vacia
		 List<TransactionForListDTO> result = this.transactionListDataService.getTransactionsForListWallet(this.page, this.SIZE);
		 if(result != null && !result.isEmpty()) {
			 
			 // Cargar datos en la lista
			 transactionList.setItems(FXCollections.observableArrayList(result));
			 
			// Actualizar total de paginas
				this.totalPages = this.transactionListDataService.getTotalPagesByLastQuery();

			}

	        // Actualizar el estado de los botones (habilitar/deshabilitar)
	        updateButtonState();
	    }

	    public void nextPage() {
	        if (page < totalPages - 1) {
	        	// Aumentar paginacion
				this.page++;
				// Cargar la siguiente página
	            loadPage();
	            
	        }
	    }

	    public void previousPage() {
	        if (page > 0) {
	        	// Disminuir paginacion
	        	this.page--;
	        	// Cargar la página anterior
	            loadPage(); 
	        }
	    }

	    private void updateButtonState() {
	        // Deshabilitar el botón "siguiente" si estamos en la última página
	        nextButton.setDisable(page == totalPages - 1);

	        // Deshabilitar el botón "anterior" si estamos en la primera página
	        previousButton.setDisable(page == 0);
	    }
	
	public void goBack(ActionEvent event) {
		this.goBackService.loadNewView(event, "/fxml/wallet-view.fxml");
	}
	
}
