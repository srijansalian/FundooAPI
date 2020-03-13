package com.bridgelabz.fundoonotes.response;

import lombok.Data;

/**
 * 
 * @author Srijan Kumar Class for the store all the Response
 */
@Data
public class Response {
	private String message;
	private int statuscode;
	private Object obj;

	public Response(String token, int statuscode, Object obj) {
		super();
		this.message = token;
		this.statuscode = statuscode;
		this.obj = obj;
	}

	public Response(String token, int statuscode) {
		super();
		this.message = token;
		this.statuscode = statuscode;

	}

	public Response(String token) {
		super();
		this.message = token;

	}

	public Response(String token, Object obj) {
		super();
		this.message = token;

		this.obj = obj;
	}
}
