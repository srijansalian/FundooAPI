package com.bridgelabz.fundoonotes.exception;

import lombok.Getter;

/**
 * The Class for handle Label Exception
 * 
 * @author Srijan Kumar
 *
 */

@Getter
public class LabelException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String message;

	public LabelException(String message) {
		super(message);
		this.message = message;

	}

}
