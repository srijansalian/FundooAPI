package com.bridgelabz.fundoonotes.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
 * @author Srijan Kumar
 *
 */
/*
 * UserDto implementation
 */
@Data
@Component
public class UserDto {
	private String name;
	private String email;
	private String password;
	private long mobilenumber;

}
