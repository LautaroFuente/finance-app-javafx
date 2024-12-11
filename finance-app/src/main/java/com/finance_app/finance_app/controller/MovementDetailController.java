package com.finance_app.finance_app.controller;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;

import com.finance_app.finance_app.DTO.TransactionForListDTO;
import com.finance_app.finance_app.service.GoBackService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MovementDetailController {

	@Autowired
	private GoBackService goBackService;
	
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
	
	
	public void initialize() {
		// Cargar datos de los label con los datos de la transaccion que recibe el controlador
		this.initLabels();
	}
	
	private void initLabels() {
		this.transactionType.setText(this.transaction.getType());
		this.transactionAmount.setText(((Double)this.transaction.getAmount()).toString());
		this.transactionNameCategory.setText(this.transaction.getNameCategory());
		// Formatear fecha
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		String date = this.transaction.getDate().format(formatter);
		this.transactionDate.setText(date);
	}
	
	public void goBack(ActionEvent event) {
		this.goBackService.goBack(event, "/fxml/movement-list-view.fxml");
	}
}
