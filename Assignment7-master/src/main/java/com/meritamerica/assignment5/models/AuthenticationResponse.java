package com.meritamerica.assignment5.models;

public class AuthenticationResponse {
	
	
	private final String jwt;

	public AuthenticationResponse(String jwt) {
		this.jwt = jwt;
	}

	//only getter
	public String getJwt() {
		return jwt;
	}
	
	
	
	
	

}
