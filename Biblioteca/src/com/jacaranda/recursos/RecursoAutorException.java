package com.jacaranda.recursos;

public class RecursoAutorException extends Exception {

	public RecursoAutorException() {
		super();
	}

	public RecursoAutorException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RecursoAutorException(String message, Throwable cause) {
		super(message, cause);
		}

	public RecursoAutorException(String message) {
		super(message);
	}

	public RecursoAutorException(Throwable cause) {
		super(cause);
	}
	
}
