package com.finance_app.finance_app.controller;

import java.awt.Button;

import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;

@Controller
public class HomeController {

	@FXML
    private Button button;

    @FXML
    public void onClick() {
        System.out.println("¡Botón clickeado!");
    }
}
