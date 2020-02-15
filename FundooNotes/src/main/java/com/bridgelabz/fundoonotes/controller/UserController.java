package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LoginInformation;
import com.bridgelabz.fundoonotes.dto.PasswordUpdate;
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
	 * API for the Registration
	 */
	@PostMapping("/user/registration")
	public ResponseEntity<Response> registration(@RequestBody UserDto information) {

		boolean result = service.register(information);
		if (result) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("Registration Successfull", 200, information));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
				.body(new Response("User has been already Registered", 208, information));
	}

	/*
	 * This API is used to Verify the token from the user
	 */
	@GetMapping("/verify/{token}")
	public ResponseEntity<Response> userVerfication(@PathVariable("token") String token) throws Exception {
		boolean update = service.verify(token);
		if (update) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response(token, 200, "VERIFIED"));

		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response(token, 401, "Not Verified"));
	}

	/*
	 * API is used to login
	 */
	@PostMapping("user/login")
	public ResponseEntity<UserDetail> login(@RequestBody LoginInformation information) {
		UserInformation userInformation = service.login(information);
		if (userInformation != null) {
			// String token = generate.JwtToken(userInformation.getUserId());
			/*
			 * Token must be added in login display
			 */
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("login successfull", information.getEmail())
					.body(new UserDetail("Login Sucessfull", 200, information));
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserDetail("Login is failed", 400, information));

	}
	/*
	 * API for the Update of the password with the Token
	 */

	@PostMapping("/user/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam("email") String email) {

		System.out.println(email);

		boolean result = service.isUserExist(email);
		if (result) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("User Exists", 200, email));

		}
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new Response("User Does not exit in the given email id", 400, email));

	}

	/*
	 * API used for the update
	 */
	@PutMapping("/user/update/{token}")
	public ResponseEntity<Response> update(@PathVariable("token") String token, @RequestBody PasswordUpdate update) {
		boolean result = service.update(update, token);
		if (result) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new Response("password updated successfully", 200, update));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("password  does not match", 402, update));

	}
	/*
	 * API used to get all the users
	 */
	@GetMapping("user/getusers")
	public ResponseEntity<Response> getUsers(){
		List<UserInformation> users = service.getUsers();
		
		
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("The Registered user are",200,users));
		
	}
	/*
	 * API used to get the single record
	 */
	@GetMapping("user/getallusers")
	public ResponseEntity<Response> getOneUser(@RequestHeader("token")String token){
		UserInformation user =service.getsingleUser(token);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("The User Details",200,user));
	}
	

}
