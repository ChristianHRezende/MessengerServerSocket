package br.com.chrezende.messenger.socket.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.chrezende.messenger.controller.HomeController;
import br.com.chrezende.messenger.controller.alert.AlertException;
import br.com.chrezende.messenger.controller.alert.AlertSuccess;
import br.com.chrezende.messenger.controller.client.ClientListController;
import br.com.chrezende.messenger.controller.message.MessageListController;
import br.com.chrezende.messenger.db.DbService;
import javafx.application.Platform;

public class ServerSocketService {

	private static ServerSocket server;
	private static List<SocketThread> socketList = new ArrayList<SocketThread>();

	/**
	 * Stop server
	 */
	public static void stop() {
		if (server != null) {
			try {
				server.close();
				HomeController.changeServerStatus(null);
				new AlertSuccess("Server stopped");
			} catch (IOException e) {
				// Show message server
				new AlertException("Failed on stop server");
			}
		}
	}

	/**
	 * Start server
	 */
	public static void start(String portString) {

		// check if server is running
		if (server != null && !server.isClosed()) {
			throw new IllegalStateException();
		}

		// Check port is number
		if (!portString.isEmpty() && !StringUtils.isNumeric(portString)) {
			throw new IllegalArgumentException();
		}

		// Start server
		int port;
		port = portString.isEmpty() ? 3322 : Integer.parseInt(portString);

		Socket socket = null;
		try {
			//Start server and change status
			server = new ServerSocket(port);
			HomeController.changeServerStatus(port);

		} catch (IOException e) {
			//stop server
			stop();
		}

		//Start listener sockets
		while (!server.isClosed()) {
			try {
				// Check new connection socket
				socket = server.accept();

				// Start thread of socket
				SocketThread thread = new SocketThread(server, socket);
				thread.start();

				// Add to list threads
				socketList.add(thread);
			} catch (IOException e) {
				// Server is closed
				//Interrupt all threads
				socketList.forEach((thread) -> Thread.interrupted());

				// Remove all users connected
				DbService.clearDb();

				// Load Content lists
				loadLists();
			}
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
