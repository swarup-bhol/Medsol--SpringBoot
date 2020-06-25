package com.sm.exception;

public class FileFormatException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public FileFormatException(String message) {
		super(message);
	}

	public FileFormatException(String message, Throwable cause) {
		super(message, cause);
	}

}
