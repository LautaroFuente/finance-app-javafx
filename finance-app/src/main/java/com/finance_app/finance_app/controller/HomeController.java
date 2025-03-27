package com.finance_app.finance_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.finance_app.finance_app.service.GoBackService;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@Controller
public class HomeController {
	
	@Autowired
	private GoBackService loadNewViewService;
	
	@FXML
    private Button buttonRegister;
	
	@FXML
    private Button buttonLogin;
	
	@FXML
    private Button buttonHelp;
	
	@FXML
	private Button buttonExit;

    @FXML
    public void onClickRegister(ActionEvent event) {
        this.loadNewViewService.goBack(event, "/fxml/register-view.fxml");
    }
    
    @FXML
    public void onClickLogin(ActionEvent event) {
    	this.loadNewViewService.goBack(event, "/fxml/login-view.fxml");
    }
    
    @FXML
    public void onClickHelp(ActionEvent event) {
    	this.loadNewViewService.goBack(event, "/fxml/help-view.fxml");
    }
    
    @FXML
    public void onClickExit() {
    	Platform.exit(); // Cierra JavaFX
    }

}
