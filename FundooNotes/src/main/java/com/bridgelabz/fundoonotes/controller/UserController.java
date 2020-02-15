package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LoginInformation;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.UserDetail;
import com.bridgelabz.fundoonotes.service.Services;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;
/**
 * 
 * @author Srijan Kumar
 *
 */
@RestController
public class UserController {
	
	@Autowired
	private Services service;

	@Autowired
	private JwtGenerator generate;
/*
 * Controller for the Registration
 */
	@PostMapping("/user/registration")
	public ResponseEntity<Response> registration(@RequestBody UserDto information){
		
		boolean result = service.register(information);
		if(result) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Registration Successfull", 200, information));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new Response("User has been already Registered", 208, information));
	}
	/*
	 * This Controller is used to Verify the token from the user
	 */
	@GetMapping("/verify/{token}")
	public ResponseEntity<Response> userVerfication(@PathVariable("token")String token) throws Exception
	{
		boolean update = service.verify(token);
		if(update)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response(token, 200,"VERIFIED"));
			
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response(token,401,"Not Verified"));
	}
	
	/*
	 * This is used to login 
	 */
	@PostMapping("user/login")
	public ResponseEntity<UserDetail> login(@RequestBody LoginInformation information){
		UserInformation userInformation = service.login(information);
		if(userInformation !=null) {
			//String token = generate.JwtToken(userInformation.getUserId());
			/*
			 * Token must be added in login  display
			 */
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("login successfull",information.getEmail()).body(new UserDetail("Login Sucessfull",200,information));
		}
		
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserDetail("Login is failed",400,information));
		
	}
	

}
