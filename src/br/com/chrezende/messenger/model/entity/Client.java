package br.com.chrezende.messenger.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Client {

	private String address;

	private String username;

	private List<Message> messageList;

	public Client() {
		messageList = new ArrayList<Message>();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Message> getMessageList() {
		return messageList;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return username;
	}

	/**
	 * Add a message to list
	 * 
	 * @param message
	 */
	public void addToMessageList(Message message) {
		this.messageList.add(message);
	}

	/**
	 * Add a messageList to list
	 * 
	 * @param message
	 */
	public void addToMessageList(List<Message> messageList) {
		this.messageList.addAll(messageList);
	}

}
