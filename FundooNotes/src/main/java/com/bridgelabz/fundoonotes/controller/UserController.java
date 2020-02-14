package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.Services;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

@RestController
public class UserController {
	
	@Autowired
	private Services service;

	@Autowired
	private JwtGenerator generate;

	@PostMapping("/user/registration")
	public ResponseEntity<Response> registration(@RequestBody UserDto information){
		
		boolean result = service.register(information);
		if(result) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Registration Successfull", 200, information));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new Response("User has been already Registered", 208, information));
	}

}
