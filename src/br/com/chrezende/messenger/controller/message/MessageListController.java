package br.com.chrezende.messenger.controller.message;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.chrezende.messenger.Main;
import br.com.chrezende.messenger.controller.HomeController;
import br.com.chrezende.messenger.model.entity.Message;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class MessageListController extends HomeController implements Initializable {

	@FXML
	private ListView<Message> messageList;

	@FXML
	private Button buttonRefresh;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		getMessages();
	}

	private void getMessages() {
			List<Message> list = Main.getMessageList();
			messageList.setItems(FXCollections.observableArrayList(list));		
	}
	
	@FXML
	void clickRefreshButtonHandler(ActionEvent event) {
		getMessages();
	}

}
