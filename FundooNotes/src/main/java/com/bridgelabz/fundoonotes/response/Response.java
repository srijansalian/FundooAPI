package com.bridgelabz.fundoonotes.response;



import lombok.Data;
/**
 * 
 * @author Srijan Kumar
 *Class for the store all the Response
 */
@Data
public class Response {
	private String token;
	private int statuscode;
	private Object obj;

	public Response(String token, int statuscode, Object obj) {
		super();
		this.token = token;
		this.statuscode = statuscode;
		this.obj = obj;
	}
	public Response(String token, int statuscode) {
		super();
		this.token = token;
		this.statuscode = statuscode;
		
	}
}
