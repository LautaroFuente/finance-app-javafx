package com.finance_app.finance_app.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.finance_app.finance_app.DTO.CategoryPercentageDTO;
import com.finance_app.finance_app.DTO.TransactionForListDTO;
import com.finance_app.finance_app.enums.TransactionType;
import com.finance_app.finance_app.service.ChartDataService;
import com.finance_app.finance_app.service.GoBackService;
import com.finance_app.finance_app.service.TransactionListDataService;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.shape.Circle;

public class WalletController {
	
	@Autowired
	private ChartDataService chartDataService;
	
	@Autowired
	private GoBackService goBackService;
	
	@Autowired
	private TransactionListDataService transactionListDataService;

	@FXML
	private PieChart chart;
	
	@FXML
	private Circle hole;
	
	@FXML
	private Button closeSession;
	
	@FXML
	private Button notifications;
	
	@FXML
	private Button allTransactions;
	
	@FXML
	private Button asignLimit;
	
	@FXML
	private TableView<TransactionForListDTO> transactionList;
	
	@FXML
	private TableColumn<TransactionForListDTO, String> transactionType;
	
	@FXML
	private TableColumn<TransactionForListDTO, 	Double> transactionAmount;
	
	@FXML
	private TableColumn<TransactionForListDTO, String> transactionCategory;
	
	@FXML
	private TableColumn<TransactionForListDTO, LocalDateTime> transactionDate;
	
	public void initialize() {
		// Configurar grafico dona
		this.initChart();
		
		// Configurar lista transacciones
		this.initList();
		
	}
	
	private void initChart() {
		// Crear las porciones del gráfico de dona
		List<CategoryPercentageDTO> percentages = this.chartDataService.getPercentageForCategory();
		
		for(CategoryPercentageDTO percentage : percentages) {
			PieChart.Data slice = new PieChart.Data(percentage.getName(), percentage.getPercentage());
			// Asignar las porciones al PieChart
			chart.getData().add(slice);
		}

		// Configurar propiedades adicionales del gráfico
		chart.setLabelsVisible(true);

		// Cambiar el tamaño del hueco (Circle) si es necesario
		hole.setRadius(60);
	}
	
	private void initList() {
		// Configuramos los datos de las columnas para mostrar los campos necesarios en cada celda
		transactionType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
		transactionAmount.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getAmount()));
		transactionCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNameCategory()));
		//Falta la de la fecha

		
		// Cargar datos en la lista
		transactionList.setItems(FXCollections.observableArrayList(this.transactionListDataService.getTransactionsForListWallet()));
	}
	
	public void closeMySession(ActionEvent event) {
		this.goBackService.goBack(event, "/fxml/home-view.fxml");
	}
}
