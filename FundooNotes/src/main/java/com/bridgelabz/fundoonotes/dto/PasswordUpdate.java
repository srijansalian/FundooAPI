package com.bridgelabz.fundoonotes.dto;

import lombok.Data;

/**
 * 
 * @author Srijan Kumar
 *
 */
@Data
public class PasswordUpdate {
	private String email;
	private String password;
	private String confirmPassword;

}
