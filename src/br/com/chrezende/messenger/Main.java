package br.com.chrezende.messenger;

import java.util.ArrayList;
import java.util.List;

import br.com.chrezende.messenger.model.entity.Message;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private static List<Message> messageList = new ArrayList<Message>();

	@Override
	public void start(Stage primaryStage) {
		ServerSocketService.start();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static void addToList(Message message){
		messageList.add(message);
	}
	
}
