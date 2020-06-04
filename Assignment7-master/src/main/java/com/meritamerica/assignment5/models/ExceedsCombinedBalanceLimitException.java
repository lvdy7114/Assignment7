package com.meritamerica.assignment5.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExceedsCombinedBalanceLimitException extends Exception {
	
	static final double combinedBalanceLimit = 250000;
	
	public ExceedsCombinedBalanceLimitException() {}
	
	public ExceedsCombinedBalanceLimitException (String message) {
		super(message);
	}

	public static double getCombinedbalancelimit() {
		return combinedBalanceLimit;
	}
	
	

}
