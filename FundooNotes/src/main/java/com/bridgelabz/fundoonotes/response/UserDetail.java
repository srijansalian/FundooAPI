package com.bridgelabz.fundoonotes.response;

import lombok.Data;

/**
 * 
 * @author Srijan Kumar
 *
 */
@Data
public class UserDetail {
	private String token;
	private int statuscode;
	private Object obj;

	public UserDetail(String token, Object obj) {
		super();
		this.token = token;
		
		this.obj = obj;
	}

}
