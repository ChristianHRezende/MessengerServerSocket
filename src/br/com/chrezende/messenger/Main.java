package br.com.chrezende.messenger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {

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

}
