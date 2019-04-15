package br.com.chrezende.messenger.socket.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import br.com.chrezende.messenger.controller.client.ClientListController;
import br.com.chrezende.messenger.controller.message.MessageListController;
import br.com.chrezende.messenger.db.DbService;
import br.com.chrezende.messenger.model.entity.Client;
import br.com.chrezende.messenger.model.entity.Message;
import javafx.application.Platform;

/**
 * Thread for control in and out infos with socket client
 * 
 * @author chris
 *
 */
public class SocketThread extends Thread {

	protected ServerSocket server;
	protected Socket socket;

	// Messages are separed username + #!@ + message
	private static String MESSAGE_SEPARATOR = "#!@";

	public SocketThread(ServerSocket server, Socket clientSocket) {
		this.server = server;
		this.socket = clientSocket;
	}

	public void run() {

		BufferedReader input;
		try {

			//get address client
			String address = socket.getInetAddress() + ":" + socket.getPort();

			//initialize in and output readers
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

			// start listener message
			try {
				while (!server.isClosed()) {

					String message = input.readLine();
					if (message == null)
						break;
					if (message == "")
						break;

					// get the message and separate to create a client with message
					Client client = getClientMessage(message, address);
					
					if (client != null) {
						// Add the new client or udpate if exists
						DbService.addToClientList(client);

						// Send success message to client
						sendMessageToClient("success", output);

						// Load lists on view
						loadLists();
					}else {
						sendMessageToClient("fail", output);
					}

				}
			} catch (IOException e) {
				//Send fail message
				sendMessageToClient("fail", output);
			}

			try {
				socket.close();
				// Remove client online
				DbService.removeClient(address);
				// Refresh
				loadLists();

			} catch (IOException e) {
				System.out.println(e);
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println(e);
		}

	}

	/**
	 * Send message to client socket
	 * 
	 * @param message
	 * @param output
	 * @throws IOException
	 */
	private static void sendMessageToClient(String message, PrintWriter output) throws IOException {
		if (output != null) {
			output.println(message);
		}
	}

	/**
	 * Get Response and add to list
	 * 
	 * @param entrada
	 */
	private static Client getClientMessage(String in, String address) {

		// Get current date time
		LocalDateTime dateTime = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(dateTime);

		// Get last id and add 1
		Long _id = timestamp.getTime();

		if (in.contains(MESSAGE_SEPARATOR) && in.split(MESSAGE_SEPARATOR).length == 2) {
			// username#!@message
			String username = in.split(MESSAGE_SEPARATOR)[0];
			String message = in.split(MESSAGE_SEPARATOR)[1];

			// Create a client obj
			Client obj = new Client();
			obj.setUsername(username);
			obj.setAddress(address);
			obj.addToMessageList(new Message(_id, username, dateTime, message));

			return obj;
		} else {
			return null;
		}
	}

	/**
	 * Refresh lists whem receive messages
	 */
	private static void loadLists() {
		// Run JavaFX load lists
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// LoadClientList
				ClientListController.refreshList();

				// LoadMessages
				MessageListController.refreshList();
			}
		});

	}

}