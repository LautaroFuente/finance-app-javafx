package com.finance_app.finance_app.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.finance_app.finance_app.DTO.CategoryPercentageDTO;
import com.finance_app.finance_app.DTO.TransactionForListDTO;
import com.finance_app.finance_app.entities.Wallet;
import com.finance_app.finance_app.service.ChartDataService;
import com.finance_app.finance_app.service.GoBackService;
import com.finance_app.finance_app.service.NotificationService;
import com.finance_app.finance_app.service.TransactionListDataService;
import com.finance_app.finance_app.service.WalletService;
import com.finance_app.finance_app.utils.SessionManager;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


@Controller
public class WalletController {
	
	@Autowired
	private ChartDataService chartDataService;
	
	@Autowired
	private GoBackService goBackService;
	
	@Autowired
	private TransactionListDataService transactionListDataService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private WalletService walletService;

	@FXML
	private PieChart chart;
	
	@FXML
	private Circle hole;
	
	@FXML
	private Button closeSession;
	
	@FXML
	private Button buttonNotifications;
	
	@FXML
	private Button allTransactions;
	
	@FXML
	private Button buttonAsignLimit;
	
	@FXML
	private Button buttonAddTransaction;
	
	@FXML
	private Label totalWallet;
	
	@FXML
	private StackPane notificationStack;
	
	@FXML
	private ImageView bellIcon;
	
	@FXML
	private Label notificationCountLabel;
	
	@FXML
	private TableView<TransactionForListDTO> transactionList;
	
	@FXML
	private TableColumn<TransactionForListDTO, String> transactionType;
	
	@FXML
	private TableColumn<TransactionForListDTO, 	Double> transactionAmount;
	
	@FXML
	private TableColumn<TransactionForListDTO, String> transactionCategory;
	
	@FXML
	private TableColumn<TransactionForListDTO, String> transactionDate;
	
	private IntegerProperty notificationCount = new SimpleIntegerProperty(0);  // Propiedad reactiva para las notificaciones
	
	public void initialize() {
		// Configurar grafico dona
		this.initChart();
		
		// Configurar lista transacciones
		this.initList();
		
		// Iniciar el total de la billetera
		this.initTotalWallet();
		
		// Iniciar el icono y su numero de notificaciones
		this.initNotifications();
	}
	
	private void initNotifications() {
		// Vincular el texto del Label al contador de notificaciones
        notificationCountLabel.textProperty().bind(Bindings.format("%d", notificationCount));

        // Establecer estilo inicial para la campana de notificación
        bellIcon.setStyle("-fx-background-color: transparent;");
        // Establecer imagen inicial
        updateBellIcon();

        // Oobtener el número de notificaciones
        updateNotificationCountFromService();

	}
	
    private void updateNotificationCountFromService() {
        // Llamar a servicio para obtener el numero de notificaciones
        int newNotificationCount = this.notificationService.getNumberOfNotificationFromUser(SessionManager.getInstance().getUser().getId());

        // Actualizar la propiedad con el nuevo valor
        notificationCount.set(newNotificationCount);

        // Si el número de notificaciones es mayor que 0, mostramos el contador
        if (newNotificationCount > 0) {
            notificationCountLabel.setVisible(true); 
        } else {
            notificationCountLabel.setVisible(false); 
        }

        // Actualizar la imagen de la campana en funcion de las notificaciones
        updateBellIcon();
    }

    // Actualiza la imagen de la campana dependiendo de las notificaciones
    private void updateBellIcon() {
        if (notificationCount.get() > 0) {
        	Image image = new Image(getClass().getResourceAsStream("/images/bell_with_notifications.png"));
            bellIcon.setImage(image);
        } else {
        	Image image = new Image(getClass().getResourceAsStream("/images/bell_without_notifications.png"));
            bellIcon.setImage(image);
            
        }
    }
	
	private void initTotalWallet() {
		
		// Conseguir la billetera
		Wallet walletUser = this.walletService.getOneWallet(SessionManager.getInstance().getUser().getId());
		
		// Si no se consigue la billetera mostrar error en total
		if(walletUser == null) {
			this.totalWallet.setText("Error al cargar total");
			return;
		}
		
		// Conseguir el total de la billetera
		BigDecimal total = walletUser.getTotal();
		
		// Usar el formato de moneda para Argentina
        @SuppressWarnings("deprecation")
		Locale argentinaLocale = new Locale("es", "AR");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(argentinaLocale);

        // Formatear el total
        String formattedTotal = currencyFormat.format(total);
        
        // Asignar al label
        this.totalWallet.setText(formattedTotal);
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
		transactionAmount.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getAmount()).asObject());
		transactionCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNameCategory()));
		transactionDate.setCellValueFactory(cellData -> {
		    LocalDateTime date = cellData.getValue().getDate();  // Obtienes el LocalDateTime
		    if (date != null) {
		        // Formateas la fecha en el formato que prefieras
		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"); // ejemplo de formato
		        return new SimpleStringProperty(date.format(formatter));  // Convierte la fecha formateada a String
		    } else {
		        return new SimpleStringProperty("");  // Si la fecha es nula, mostramos una cadena vacía
		    }
		});

		
		// Cargar datos en la lista
		transactionList.setItems(FXCollections.observableArrayList(this.transactionListDataService.getTransactionsForListWallet(0, 7)));
	}
	
	public void closeMySession(ActionEvent event) {
		SessionManager.getInstance().logout();
		this.goBackService.goBack(event, "/fxml/home-view.fxml");
	}
	
	public void goToNotifications(ActionEvent event) {
		this.goBackService.goBack(event, "/fxml/notification-view.fxml");
	}
	
	public void goToAsignationLimit(ActionEvent event) {
		this.goBackService.goBack(event, "/fxml/asignation-limit-view.fxml");
	}
	
	public void goToAddTransaction(ActionEvent event) {
		this.goBackService.goBack(event, "/fxml/add-transaction-view.fxml");
	}
}
