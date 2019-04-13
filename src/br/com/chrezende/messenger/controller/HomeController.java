package br.com.chrezende.messenger.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.chrezende.messenger.controller.alert.AlertException;
import br.com.chrezende.messenger.controller.enums.PortServerEnum;
import br.com.chrezende.messenger.controller.enums.ServerStatusEnum;
import br.com.chrezende.messenger.socket.service.ServerSocketService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class HomeController implements Initializable {

	private Boolean statusServer = false;

	@FXML
	private Button startServerButton;

	@FXML
	private Button stopServerButton;

	@FXML
	private TextField startServerTextArea;

	@FXML
	private Text statusServerText;

	@FXML
	private Text serverPortText;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	void startServerButtonHandler(ActionEvent event) {
		// Start socket service
		startServerTask();
	}

	@FXML
	void stopServerButtonHandler(ActionEvent event) {
		ServerSocketService.stop();
		changeServerStatus(null);
	}

	protected void changeServerStatus(Integer port) {
		//Change status text
		statusServer = !statusServer;
		statusServerText.setText("Status: " + (statusServer == true ? ServerStatusEnum.ONLINE.toString().toLowerCase()
				: ServerStatusEnum.OFFLINE.toString().toLowerCase()));
		
		//Change Port number
		if (port != null && port > 0) {
			serverPortText.setText("Port: " + port + "");
		} else {
			serverPortText.setText("Port: " + PortServerEnum.NONE.toString().toLowerCase());
		}
	}

	private void startServerTask() {
		Task startServerTask = new Task() {

			/**
			 * Call start server
			 */
			@Override
			protected Integer call() throws Exception {
				return ServerSocketService.start(startServerTextArea.getText());
			}

			@Override
			protected void succeeded() {
				super.succeeded();
				changeServerStatus((Integer) getValue());
			}

			/**
			 * Exception control when task failed
			 */
			@Override
			protected void failed() {
				super.failed();
				if (getException().getClass() == IllegalStateException.class) {
					new AlertException("Server is running, stop server first");
				} else if (getException().getClass() == IllegalArgumentException.class) {
					new AlertException("Port has to be a number");
				} else {
					new AlertException("Server failed");
				}

			}
		};
		// Start thread
		Thread t = new Thread(startServerTask);
		t.setDaemon(true);
		t.start();
	}
}
