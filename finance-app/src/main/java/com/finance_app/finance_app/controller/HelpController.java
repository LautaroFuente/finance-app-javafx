package com.finance_app.finance_app.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.finance_app.finance_app.service.GoBackService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HelpController {

	@Autowired
	private GoBackService goBackService;
	
	@FXML
	private Button buttonBack;
	
	public void goBack(ActionEvent event) {
		this.goBackService.goBack(event, "/fxml/home-view.fxml");
	}
}
