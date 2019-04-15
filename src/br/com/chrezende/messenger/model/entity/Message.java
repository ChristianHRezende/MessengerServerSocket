package br.com.chrezende.messenger.model.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {

	private Long _id;
	private String client;
	private LocalDateTime dateTime;
	private String message;

	public Message(Long _id, String client, LocalDateTime dateTime, String message) {
		super();
		this._id = _id;
		this.client = client;
		this.dateTime = dateTime;
		this.message = message;
	}

	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		String formatedDateTime = dateTime.format(formatter);

		return formatedDateTime + " : " + message;
	}

}
