package br.com.chrezende.messenger.exception;

import java.io.Serializable;

public class InvalidStageException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	public InvalidStageException() {
		super("Invalid stage");
	}
}
