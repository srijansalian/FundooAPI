package com.bridgelabz.fundoonotes.response;

import org.springframework.http.HttpStatus;

import lombok.Data;
/**
 * 
 * @author Srijan Kumar
 *
 */
@Data
public class ExceptionResponse {
	String message;
	HttpStatus code;

	public ExceptionResponse(String message, HttpStatus code) {
		this.message = message;
		this.code = code;

	}

	public ExceptionResponse() {

	}

}
