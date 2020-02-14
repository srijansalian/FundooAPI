package com.bridgelabz.fundoonotes.exception;

import lombok.Getter;
/**
 * 
 * @author Srijan Kumar
 *
 */
/*
 * The class for the User Exceptions
 */
@Getter
public class UserException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String message;

	public UserException(String message) {
		super(message);
		this.message = message;
	}

}
