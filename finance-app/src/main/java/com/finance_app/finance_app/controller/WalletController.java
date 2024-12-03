package com.finance_app.finance_app.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.finance_app.finance_app.service.GoBackService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;

public class WalletController {
	
	@Autowired
	private GoBackService goBackService;

	@FXML
	private PieChart chart;
	
	@FXML
	private Button closeSession;
	
	@FXML
	private Button notifications;
	
	@FXML
	private Button allTransactions;
	
	@FXML
	private Button asignLimit;
	
	public void initialize() {
		
	}
	
	public void closeMySession(ActionEvent event) {
		this.goBackService.goBack(event, "/fxml/home-view.fxml");
	}
}
