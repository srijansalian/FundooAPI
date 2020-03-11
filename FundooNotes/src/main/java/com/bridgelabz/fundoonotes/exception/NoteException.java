package com.bridgelabz.fundoonotes.exception;

import lombok.Getter;

/**
 * The Class for the Note Exception
 * 
 * @author Srijan Kumar
 *
 */
@Getter
public class NoteException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;

	public NoteException(String message) {
		super(message);
		this.message = message;

	}

}
