package br.com.chrezende.messenger.controller.navigation;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.com.chrezende.messenger.exception.InvalidSceneException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SceneNavigator {

	private static String DEFAULT_PACKAGE = "../../view/";
	private static String TYPE_FILE = ".fxml";

	private static Stage stage;
	private static Map<String, String> scenes;

	public SceneNavigator(Stage stage) {
		if (stage != null) {
			SceneNavigator.stage = stage;
			scenes = new HashMap<String, String>();
			scenes.put("home", "Home");

		} else {
			throw new NullPointerException("Stage is null");
		}
	}

	public static void navigate(String sceneName) {
		try {
			if (sceneName != null) {
				String sceneFileName = scenes.get(sceneName);
				if (sceneFileName != null) {
					Pane root = FXMLLoader
							.load(SceneNavigator.class.getResource(DEFAULT_PACKAGE + sceneFileName + TYPE_FILE));
					Scene scene = new Scene(root);
					stage.setScene(scene);
				} else {
					throw new InvalidSceneException();
				}
			} else {
				throw new NullPointerException("Scene is null");
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (InvalidSceneException e) {
			System.out.println(e.getMessage());
		}
	}

}
