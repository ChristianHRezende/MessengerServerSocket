package br.com.chrezende.messenger.controller.client;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.chrezende.messenger.controller.HomeController;
import br.com.chrezende.messenger.controller.message.MessageListController;
import br.com.chrezende.messenger.db.DbService;
import br.com.chrezende.messenger.model.entity.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class ClientListController extends HomeController implements Initializable {

	//Observable list control content dynamic on client list fx
	private static ObservableList<Client> obsevableClientList;

	@FXML
	private ListView<Client> clientList;

	@FXML
	private Button buttonRefresh;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Instace empty list
		obsevableClientList = FXCollections.observableArrayList();
		clientList.setItems(obsevableClientList);
	}

	@FXML
	void clickRefreshButtonHandler(ActionEvent event) {
		//Refresh list content
		refreshList();
	}

	@FXML
	void itemListClickHandler(MouseEvent event) {
		// Set client selected on message list
		MessageListController
				.setSelectedClient(clientList.getItems().get(clientList.getSelectionModel().getSelectedIndex()));
	}

	/**
	 * refresh list
	 */
	public static void refreshList() {
		// get the selected from db and refresh list
		obsevableClientList.clear();
		List<Client> list = DbService.getClientList();
		if (list != null) {
			obsevableClientList.addAll(list);
		}

	}

}
