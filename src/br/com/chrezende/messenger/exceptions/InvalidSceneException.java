package br.com.chrezende.messenger.exceptions;

import java.io.Serializable;

public class InvalidSceneException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	public InvalidSceneException() {
		super("Invalid scene");
	}

	public InvalidSceneException(String errorMessage) {
		super(errorMessage);
	}
}
