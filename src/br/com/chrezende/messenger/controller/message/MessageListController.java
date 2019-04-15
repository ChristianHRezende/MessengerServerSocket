package br.com.chrezende.messenger.controller.message;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.chrezende.messenger.controller.HomeController;
import br.com.chrezende.messenger.db.DbService;
import br.com.chrezende.messenger.model.entity.Client;
import br.com.chrezende.messenger.model.entity.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class MessageListController extends HomeController implements Initializable {

	//The client selected on client list
	private static Client selected;

	//Observable list controle dynamic content on list
	private static ObservableList<Message> observableMessageList;

	@FXML
	private ListView<Message> messageList;

	@FXML
	private Button buttonRefresh;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//new empty list 
		observableMessageList = FXCollections.observableArrayList();
		messageList.setItems(observableMessageList);
	}

	@FXML
	private void clickRefreshButtonHandler(ActionEvent event) {
		//Refresh a list
		refreshList();
	}

	/**
	 * Set selected client and refresh list messages
	 * 
	 * @param client
	 */
	public static void setSelectedClient(Client client) {
		selected = client;
		refreshList();
	}

	/**
	 * Refresh list
	 */
	public static void refreshList() {
		// get the selected from db and refresh list
		observableMessageList.clear();
		if (selected != null) {
			Client client = DbService.getClient(selected);
			if (client != null) {
				observableMessageList.addAll(client.getMessageList());
			}
		}

	}

}
