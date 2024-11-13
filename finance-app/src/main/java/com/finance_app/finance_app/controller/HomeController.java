package com.finance_app.finance_app.controller;

import java.awt.Button;

import javafx.fxml.FXML;

public class HomeController {

	@FXML
    private Button button;

    @FXML
    public void onClick() {
        System.out.println("¡Botón clickeado!");
    }
}
