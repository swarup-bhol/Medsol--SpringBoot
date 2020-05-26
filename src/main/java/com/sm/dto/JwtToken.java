package com.sm.dto;

public class JwtToken {


    private String token;
    private String email;
    private long userId;

    public JwtToken(){

    }

    public JwtToken(String token, String email,long userId){
        this.token = token;
        this.email = email;
        this.userId = userId;	
    }

    public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public JwtToken(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return email;
    }

    public void setUsername(String email) {
        this.email = email;
    }
}
