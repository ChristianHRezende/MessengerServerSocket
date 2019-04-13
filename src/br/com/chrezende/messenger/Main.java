package br.com.chrezende.messenger;

import java.util.ArrayList;
import java.util.List;

import br.com.chrezende.messenger.controller.navigation.SceneNavigator;
import br.com.chrezende.messenger.model.entity.Message;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	private static List<Message> messageList = new ArrayList<Message>();

	@Override
	public void start(Stage primaryStage) {

		// Start navigation
		if (primaryStage != null) {
			new SceneNavigator(primaryStage);
		}

		// Start Main Scene
		try {
			Pane root = FXMLLoader.load(getClass().getResource("view/Home.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void addToList(Message message) {
		messageList.add(message);
	}

	public static List<Message> getMessageList() {
		return messageList;
	}
}
