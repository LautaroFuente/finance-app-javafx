package com.finance_app.finance_app.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.finance_app.finance_app.entities.Category;
import com.finance_app.finance_app.entities.LimitCategory;
import com.finance_app.finance_app.entities.User;
import com.finance_app.finance_app.service.CategoryService;
import com.finance_app.finance_app.service.GoBackService;
import com.finance_app.finance_app.service.LimitCategoryService;
import com.finance_app.finance_app.utils.SessionManager;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@Controller
public class AsignationLimitController {

	@Autowired
	private GoBackService goBackService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private LimitCategoryService limitCategoryService;
	
	@FXML
	private DatePicker dateFrom;
	
	@FXML
	private DatePicker dateTo;
	
	@FXML
	private TextField maxAmount;
	
	@FXML
	private ComboBox<Category> selectedCategory;
	
	@FXML
	private Button backButton;
	
	@FXML
	private Button buttonAccept;
	
	@FXML
	private Label messageAboutAsignation;
	
	public void initialize() {
		
		// Iniciar valores del combo box
		this.initComboBox();
		
		// Iniciar fecha desde donde empiezan a mostrar ambos calendarios
		this.dateFrom.setValue(LocalDate.now());
		
		this.dateTo.setValue(LocalDate.now());
	}
	
	private void initComboBox() {
		
		// Obtener lista de categorias
		List<Category> categoryList = this.categoryService.getAllCategory();
		
		// Asignar lista de categorias al combo box
		this.selectedCategory.setItems(FXCollections.observableArrayList(categoryList));
	}
	
	public void acceptAssign(ActionEvent event) {
		
		// Obtener la categoria que se selecciono
		Category categorySelected = this.selectedCategory.getValue();
		
		// Obtener el usuario activo
		User user = SessionManager.getInstance().getUser();
		
		// Obtener el monto ingresado como un String
        String amountString = maxAmount.getText();

        // Convertir el monto a BigDecimal
        BigDecimal amount = null;
        try {
            // Eliminar posibles espacios en blanco y convertir el String a BigDecimal
            if (!amountString.trim().isEmpty()) {
                amount = new BigDecimal(amountString.trim());
            } else {
                // Si no se ha ingresado un monto
                System.out.println("El monto no puede estar vacío");
            }
        } catch (NumberFormatException e) {
            // Si el String no es un número válido
            System.out.println("Error: El monto ingresado no es válido.");
        }
        
        // Obtener fechas en el formato necesario
        LocalDateTime dateFromFormatted = this.dateFrom.getValue().atStartOfDay();
        LocalDateTime dateToFormatted = this.dateTo.getValue().atStartOfDay();
		
		// Crear una nueva asignacion limite a esa categoria si se agregaron todos los campos y todos son validos
        if(allFieldsAreCorrect()) {
        	LimitCategory limitCategory = new LimitCategory(user, categorySelected, amount, dateFromFormatted, dateToFormatted);
    		this.limitCategoryService.addLimitCategory(limitCategory);
        }
		
	}
	
	// Verificar si todos los campos son validos
	private boolean allFieldsAreCorrect() {
		return true;
	}
	
	public void goBack(ActionEvent event) {
		this.goBackService.goBack(event, "/fxml/wallet-view.fxml");
	}
}
