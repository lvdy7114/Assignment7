package com.meritamerica.assignment5.models;

@SuppressWarnings("serial")
public class NegativeAmountException extends Exception {
	
	public NegativeAmountException() {}
	
	public NegativeAmountException(String message) {
		super(message);
	}

}
