package br.com.chrezende.messenger.socket.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;

import br.com.chrezende.messenger.controller.alert.AlertException;
import br.com.chrezende.messenger.controller.alert.AlertSuccess;
import br.com.chrezende.messenger.model.entity.Message;

public class ServerSocketService {

	private static ServerSocket server;

	/**
	 * Stop server
	 */
	public static void stop() {
		if (server != null) {
			try {
				server.close();
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
	public static Integer start(String portString) {

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

		try {

			server = new ServerSocket(port);
			System.out.println("Server waiting for client on port " + server.getLocalPort());
			new Thread(() -> {
				BufferedReader input;
				// server infinite loop
				while (!server.isClosed()) {
					try {
						Socket socket = server.accept();
						System.out
								.println("New connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
						input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

						// start listener message
						try {
							while (true) {
								String message = input.readLine();
								if (message == null)
									break;
								if (message == "")
									break;
								System.out.println(message);
								getClientMessage(message);
								sendMessageToClient("success", output);
							}
						} catch (IOException e) {
							sendMessageToClient("fail", output);
						}

						try {
							socket.close();
						} catch (IOException e) {
							System.out.println(e);
						}
					} catch (IOException e) {
						// TODO: handle exception
						System.out.println(e);
					}

				}

			}).start();

		}

		catch (IOException e) {
			System.out.println(e);
		}
		return port;
	}

	/**
	 * Get Response and add to list
	 * 
	 * @param entrada
	 */
	private static Message getClientMessage(String in) {

		System.out.println(in);

		// Get current date time
		LocalDateTime dateTime = LocalDateTime.now();

		// Get last id and add 1
		Long _id = dateTime.getLong(null);
		System.out.println("id = " + _id);

		// cliente#!@message
		String client = in.split("#!@")[0];
		String message = in.split("#!@")[1];

		return new Message(_id, client, dateTime, message);
	}

	private static void sendMessageToClient(String message, PrintWriter output) throws IOException {
		String message2 = message + " response sent...";
		output.println(message2);
	}

}
