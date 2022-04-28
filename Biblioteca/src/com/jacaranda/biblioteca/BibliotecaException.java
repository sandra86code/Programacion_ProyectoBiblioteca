package com.jacaranda.biblioteca;

public class BibliotecaException extends Exception {

	public BibliotecaException() {
		super();
	}

	public BibliotecaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BibliotecaException(String message, Throwable cause) {
		super(message, cause);
	}

	public BibliotecaException(String message) {
		super(message);
	}

	public BibliotecaException(Throwable cause) {
		super(cause);
	}

}
