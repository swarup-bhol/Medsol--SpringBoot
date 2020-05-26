package com.sm.exception;


public class UnautorizedAccess extends RuntimeException{

	
	private static final long serialVersionUID = 1L;
	
	public UnautorizedAccess(String message, Throwable cause) {
        super(message, cause);
    }
	
	public UnautorizedAccess(String message) {
		super(message);
	}
	

}
