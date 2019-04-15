package br.com.chrezende.messenger.controller.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertException {

	public AlertException(String errorMessage) {

		//Show alert for exception control
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Alert");
		alert.setHeaderText(null);
		alert.setContentText(errorMessage == null || errorMessage.isEmpty() ? "Ocorreu um erro" : errorMessage);
		alert.showAndWait();
	}
}
