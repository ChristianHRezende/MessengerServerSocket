package br.com.chrezende.messenger.db;

import java.util.ArrayList;
import java.util.List;

import br.com.chrezende.messenger.model.entity.Client;

/**
 * Class created for simulate a db on cache memory, stores the clients when app
 * is running
 * 
 * @author chris
 *
 */
public class DbService {

	// List of clients static
	private static List<Client> clientList = new ArrayList<Client>();

	/**
	 * Get the clients stored
	 * 
	 * @return
	 */
	public static List<Client> getClientList() {
		return clientList;
	}

	/**
	 * Add client to storage
	 * 
	 * @param client
	 */
	public static void addToClientList(Client client) {
		// Check if this client not exist
		Client clientExist = null;

		for (Client obj : clientList) {
			if (obj.getAddress().equals(client.getAddress()) && obj.getUsername().equals(client.getUsername())) {
				// If exists
				obj.addToMessageList(client.getMessageList());
				clientExist = obj;
			}
		}

		// if not
		if (clientExist == null) {
			DbService.clientList.add(client);
		}
	}

	/**
	 * Remove client by address
	 * 
	 * @param address
	 */
	public static void removeClient(String address) {
		Client clientToRemove = null;
		for (Client obj : clientList) {
			if (obj.getAddress().equals(address)) {
				clientToRemove = obj;
			}
		}
		if (clientToRemove != null) {
			clientList.remove(clientToRemove);
		}
	}

	/**
	 * Client updated client by Obj
	 * 
	 * @param client
	 * @return
	 */
	public static Client getClient(Client client) {
		for (Client dbClient : clientList) {
			if (dbClient.getAddress().equals(client.getAddress())
					&& dbClient.getUsername().equals(client.getUsername())) {
				return dbClient;
			}
		}
		return null;

	}

	/**
	 * Clear stage list when server is close
	 */
	public static void clearDb() {
		clientList.clear();
	}

}
