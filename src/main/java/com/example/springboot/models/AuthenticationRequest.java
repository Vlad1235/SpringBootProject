package com.example.springboot.models;

import java.io.Serializable;

/**
 * What User going to sent in the POST request. We define the structure of this sent.
 * 
 *
 */
public class AuthenticationRequest implements Serializable {
	
	 	private String username;
	    private String password;
	    
	    //need default constructor for serialization
	    public AuthenticationRequest(){
	    }

	    public AuthenticationRequest(String username, String password) {
	        this.setUsername(username);
	        this.setPassword(password);
	    }
	    
	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

}
