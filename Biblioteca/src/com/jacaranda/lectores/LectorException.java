package com.jacaranda.lectores;

public class LectorException extends Exception {

	public LectorException() {
		super();
	}

	public LectorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LectorException(String message, Throwable cause) {
		super(message, cause);
	}

	public LectorException(String message) {
		super(message);
	}

	public LectorException(Throwable cause) {
		super(cause);
	}

}
