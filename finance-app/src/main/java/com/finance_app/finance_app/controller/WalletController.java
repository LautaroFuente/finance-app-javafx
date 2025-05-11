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
import com.finance_app.finance_app.service.LoadNewViewService;
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
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


@Controller
public class WalletController {
	
	@Autowired
	private ChartDataService chartDataService;
	
	@Autowired
	private LoadNewViewService goBackService;
	
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
	private Label titleLabel;
	
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
	
	private IntegerProperty notificationCount = new SimpleIntegerProperty(1);  // Propiedad reactiva para las notificaciones
	
	public void initialize() {
		this.initTitle();
		
		// Configurar grafico dona
		this.initChart();
		
		// Configurar lista transacciones
		this.initList();
		
		// Iniciar el total de la billetera
		this.initTotalWallet();
		
		// Iniciar el icono y su numero de notificaciones
		this.initNotifications();
	}
	
	private void initTitle() {
		String username = SessionManager.getInstance().getUser().getName();
		this.titleLabel.setText("Hola " + username + "!" );
	}
	
	private void initNotifications() {
		// Vincular el texto del Label al contador de notificaciones
        notificationCountLabel.textProperty().bind(Bindings.format("%d", notificationCount));
        // Establecer imagen inicial
        updateBellIcon();
        // Obtener el número de notificaciones
        updateNotificationCountFromService();
	}
	
    private void updateNotificationCountFromService() {
        // Llamar a servicio para obtener el numero de notificaciones
        int newNotificationCount = this.notificationService.getNumberOfNotificationFromUser(SessionManager.getInstance().getUser().getId());

        // Actualizar la propiedad con el nuevo valor
        notificationCount.set(newNotificationCount);
    }

    // Actualiza la imagen de la campana
    private void updateBellIcon() {
        Image image = new Image(getClass().getResourceAsStream("/images/bell_notifications.png"));
        bellIcon.setImage(image);
     // Establecer estilo inicial para la campana de notificación
        bellIcon.setStyle("-fx-background-color: transparent;");
            

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
        this.totalWallet.setText("Saldo: " + formattedTotal);
	}
	
	private void initChart() {
		// Crear las porciones del gráfico de dona
		List<CategoryPercentageDTO> percentages = this.chartDataService.getPercentageForCategory();
		
		// Verificar si la lista está vacía o todos los porcentajes son cero
		boolean hasValidData = percentages.stream()
		    .anyMatch(p -> p.getPercentage() > 0);

		if (!hasValidData) {
			PieChart.Data slice = new PieChart.Data("Sin gastos", 1);
			chart.getData().add(slice);

			// Asegura que el nodo ya esté creado antes de modificar su estilo
			Platform.runLater(() -> {
			    slice.getNode().setStyle("-fx-pie-color: gray;");
			});

		} else {
		    for (CategoryPercentageDTO percentage : percentages) {
		        chart.getData().add(new PieChart.Data(percentage.getName(), percentage.getPercentage()));
		    }
		}
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
		// Hacer la tabla no editable por el usuario
		transactionList.setEditable(false);
		transactionList.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY); // Para controlar resizing
		
		// Cargar datos en la lista
		transactionList.setItems(FXCollections.observableArrayList(this.transactionListDataService.getTransactionsForListWallet(0, 7)));
	}
	
	@FXML
	public void closeMySession(ActionEvent event) {
		SessionManager.getInstance().logout();
		this.goBackService.loadNewView(event, "/fxml/home-view.fxml");
	}
	
	@FXML
	public void goToNotifications(ActionEvent event) {
		this.goBackService.loadNewView(event, "/fxml/notification-view.fxml");
	}
	
	@FXML
	public void goToAsignationLimit(ActionEvent event) {
		this.goBackService.loadNewView(event, "/fxml/asignation-limit-view.fxml");
	}
	
	@FXML
	public void goToAddTransaction(ActionEvent event) {
		this.goBackService.loadNewView(event, "/fxml/add-transaction-view.fxml");
	}
	
	@FXML
	public void goToConfiguration(ActionEvent event) {
		this.goBackService.loadNewView(event, "/fxml/configuration-view.fxml");
	}
}
