package com.crudapp.main.exception;

import org.springframework.lang.NonNull;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	String errorMessage;
	public CustomException(@NonNull final String message) {
		super(message);
	}
}
