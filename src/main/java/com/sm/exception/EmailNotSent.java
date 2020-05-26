package com.sm.exception;

public class EmailNotSent extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EmailNotSent(String message, Throwable cause) {
        super(message, cause);
    }
	
	public EmailNotSent(String message) {
		super(message);
	}

}
