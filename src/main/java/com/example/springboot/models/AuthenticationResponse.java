package com.example.springboot.models;

import java.io.Serializable;

/**
 * 
 * output structure for authentication method
 *
 */
public class AuthenticationResponse implements Serializable {
	
    private final String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
