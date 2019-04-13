package br.com.chrezende.messenger.controller.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertSuccess {

	public AlertSuccess(String message) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Success");
		alert.setHeaderText(null);
		alert.setContentText(message == null || message.isEmpty() ? "Concluido com sucesso" : message);
		alert.showAndWait();
	}
}
