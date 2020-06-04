package com.meritamerica.assignment5.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExceedsFraudSuspicionLimitException extends Exception {
	
	public ExceedsFraudSuspicionLimitException() {
		
	}
		
	public ExceedsFraudSuspicionLimitException(String message) {
		super(message);
	}
	
	
	public String toString() {
		return "Are you with the mob?";
	}
	
	
}
