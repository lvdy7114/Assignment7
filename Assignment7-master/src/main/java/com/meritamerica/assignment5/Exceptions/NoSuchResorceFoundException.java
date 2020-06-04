package com.meritamerica.assignment5.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchResorceFoundException extends Exception {

	
	public NoSuchResorceFoundException(String string) {
		super(string);
	}
}