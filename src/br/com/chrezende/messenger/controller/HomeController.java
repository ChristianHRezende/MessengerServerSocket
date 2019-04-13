package br.com.chrezende.messenger.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.chrezende.messenger.controller.alert.AlertException;
import br.com.chrezende.messenger.socket.service.ServerSocketService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HomeController implements Initializable {

	@FXML
	private Button startServerButton;

	@FXML
	private Button stopServerButton;

	@FXML
	private TextField startServerTextArea;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	void startServerButtonHandler(ActionEvent event) {
		// Start socket service
		Task tarefaCargaPg = new Task() {

			@Override
			protected Void call() throws Exception {
				ServerSocketService.start(startServerTextArea.getText());
				return null;
			}

			@Override
			protected void failed() {
				super.failed();
				if (getException().getClass() == IllegalStateException.class) {
					new AlertException("Server is running, stop server first");
				} else if (getException().getClass() == IllegalArgumentException.class) {
					new AlertException("Port has to be a number");
				}else {
					new AlertException("Server failed");
				}

			}
		};
		Thread t = new Thread(tarefaCargaPg);
		t.setDaemon(true);
		t.start();
	}

	@FXML
	void stopServerButtonHandler(ActionEvent event) {
		ServerSocketService.stop();
	}
}
