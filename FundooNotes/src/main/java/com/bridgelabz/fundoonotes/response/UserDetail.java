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

	public UserDetail(String token, int statuscode, Object obj) {
		super();
		this.token = token;
		this.statuscode = statuscode;
		this.obj = obj;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(int statuscode) {
		this.statuscode = statuscode;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}
