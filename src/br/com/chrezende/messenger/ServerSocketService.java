package br.com.chrezende.messenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

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
			} catch (IOException e) {
				// Show message server
				e.printStackTrace();
			}
		}
	}

	/**
	 * Start server
	 */
	public static void start() {
		int port;
		BufferedReader input;

		System.out.println("port = 1500 (default)");
		port = 3322;

		try {

			server = new ServerSocket(port);
			System.out.println("Server waiting for client on port " + server.getLocalPort());

			// server infinite loop
			while (true) {
				Socket socket = server.accept();
				System.out.println("New connection accepted " + socket.getInetAddress() + ":" + socket.getPort());
				input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
				
				//start listener message
				try {
					while (true) {
						String message = input.readLine();
						if (message == null)
							break;
						if (message == "")
							break;
						System.out.println(message);
						getClientMessage(message);
						sendMessageToClient("Recebemos sua msg", output);
					}
				} catch (IOException e) {
					System.out.println(e);
					//Tratar exceção
				}

				try {
					socket.close();
					System.out.println("Connection closed by client");
				} catch (IOException e) {
					//Tratar
					System.out.println(e);
				}

			}

		}

		catch (IOException e) {
			System.out.println(e);
		}
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
