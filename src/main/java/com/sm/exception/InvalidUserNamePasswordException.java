package com.sm.exception;

public class InvalidUserNamePasswordException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidUserNamePasswordException(String message, Throwable cause) {
        super(message, cause);
    }
	public InvalidUserNamePasswordException(String message) {
        super(message);
    }
}
