package com.jacaranda.lectores;

public class MultaException extends Exception {

	public MultaException() {
		super();
	}

	public MultaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MultaException(String message, Throwable cause) {
		super(message, cause);
	}

	public MultaException(String message) {
		super(message);
	}

	public MultaException(Throwable cause) {
		super(cause);
	}

}
