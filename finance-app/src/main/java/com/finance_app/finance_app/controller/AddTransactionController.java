package com.finance_app.finance_app.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.finance_app.finance_app.entities.Category;
import com.finance_app.finance_app.entities.Transaction;
import com.finance_app.finance_app.entities.User;
import com.finance_app.finance_app.enums.TransactionType;
import com.finance_app.finance_app.service.CategoryService;
import com.finance_app.finance_app.service.GoBackService;
import com.finance_app.finance_app.service.TransactionService;
import com.finance_app.finance_app.utils.SessionManager;
import com.finance_app.finance_app.validation.Validator;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@Controller
public class AddTransactionController {

	@Autowired
	private GoBackService goBackService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private CategoryService categoryService;
	
	@FXML
	private ComboBox<TransactionType> selectedType;
    
    @FXML
	private ComboBox<Category> selectedCategory;
    
    @FXML
    private TextField amountField;
    
    @FXML
    private Label errorAmountField;
    
    @FXML 
    private TextField descriptionField;
    
    @FXML
    private Button buttonSubmit;  
    
    @FXML
    private Label responseMessage;
    
    @FXML
    private Button buttonBack;
    
    public void initialize() {
		
		// Iniciar valores de los combo box
		this.initComboBox();
    }
    
    private void initComboBox() {
		
		// Obtener lista de categorias
		List<Category> categoryList = this.categoryService.getAllCategory();
		
		// Asignar lista de categorias al combo box de categorias
		this.selectedCategory.setItems(FXCollections.observableArrayList(categoryList));
		
		// Asignar valores posibles al combo box de tipos de transacciones
		this.selectedType.setItems(FXCollections.observableArrayList(TransactionType.values()));
		
		// Asignar valores por defecto a los combo box
		this.selectedType.getSelectionModel().selectFirst();
		this.selectedCategory.getSelectionModel().selectFirst();
	}
	
	@FXML
	public void onSubmit(ActionEvent event) {
		
		cleanErrorFields();
		if(!Validator.validateNumericText(this.amountField.getText())) {
		
			this.errorAmountField.setText("El monto ingresado no es válido");
		
			cleanFields();
			return;
		}
		
		// Obtener el usuario actual
		User user = SessionManager.getInstance().getUser();
		
		// Convertir el monto a numerico
		String amountText = this.amountField.getText();
		BigDecimal amount = new BigDecimal(amountText);
		
		// Obtener tipo de transaccion
		TransactionType type = this.selectedType.getValue();
		
		// Obtener categoria
		Category category = this.selectedCategory.getValue();
		
		// Crear la transaccion a agregar
		Transaction transactionForSave = new Transaction(user, amount, this.descriptionField.getText(), LocalDateTime.now(), type, category);
		String responseAdd = this.transactionService.addTransactionForUser(transactionForSave);
		
		if("Transaccion guardada exitosamente".equals(responseAdd)) {
			this.responseMessage.setText("Transaccion guardada exitosamente");	

		}else {
			this.responseMessage.setText("Error con el servidor al agregar la transaccion");
		}
	}
	
	private void cleanFields() {
		this.amountField.setText("");
	}
	
	private void cleanErrorFields() {
		this.errorAmountField.setText("");
	}
	
	public void goBack(ActionEvent event) {
		this.goBackService.goBack(event, "/fxml/wallet-view.fxml");
	}
}
