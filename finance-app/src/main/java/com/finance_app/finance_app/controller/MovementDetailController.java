package com.finance_app.finance_app.controller;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.finance_app.finance_app.DTO.TransactionForListDTO;
import com.finance_app.finance_app.service.LoadNewViewService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

@Controller
public class MovementDetailController {

	@Autowired
	private LoadNewViewService goBackService;
	
	private TransactionForListDTO transaction;

	public void setTransaction(TransactionForListDTO transaction) {
		this.transaction = transaction;
	}
	
	@FXML
	private Label transactionType;
	
	@FXML
	private Label transactionAmount;
	
	@FXML
	private Label transactionNameCategory;
	
	@FXML
	private Label transactionDate;
	
	@FXML
    private Button buttonBack;
	
	// Cargar datos de los label con los datos de la transaccion que recibe el controlador
	public void initLabels() {
		this.transactionType.setText(this.transaction.getType());
		this.transactionAmount.setText(((Double)this.transaction.getAmount()).toString());
		this.transactionNameCategory.setText(this.transaction.getNameCategory());
		// Formatear fecha
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String date = this.transaction.getDate().format(formatter);
		this.transactionDate.setText(date);
	}
	
	public void goBack(ActionEvent event) {
		this.goBackService.loadNewView(event, "/fxml/movement-list-view.fxml");
	}
}
