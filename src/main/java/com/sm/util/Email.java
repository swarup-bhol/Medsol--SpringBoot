package com.sm.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor 
public class Email {
	
	public String sender;
	public String password;
	public String host;
	public String contentType;
	public String subject;
	public String message;
	public String status;

	
}
