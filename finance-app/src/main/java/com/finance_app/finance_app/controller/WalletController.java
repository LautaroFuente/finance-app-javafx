package com.finance_app.finance_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.finance_app.finance_app.DTO.CategoryPercentageDTO;
import com.finance_app.finance_app.service.ChartDataService;
import com.finance_app.finance_app.service.GoBackService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

public class WalletController {
	
	@Autowired
	private ChartDataService chartDataService;
	
	@Autowired
	private GoBackService goBackService;

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
		
	}
	
	public void closeMySession(ActionEvent event) {
		this.goBackService.goBack(event, "/fxml/home-view.fxml");
	}
}
