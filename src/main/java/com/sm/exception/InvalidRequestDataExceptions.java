package com.sm.exception;

public class InvalidRequestDataExceptions extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InvalidRequestDataExceptions() {
        super();
    }

    public InvalidRequestDataExceptions(String message) {
        super(message);
    }

    public InvalidRequestDataExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
