package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.UserDetail;
import com.bridgelabz.fundoonotes.service.Services;

/**
 * 
 * @author Srijan Kumar
 *
 */
@RestController

public class UserController {

	@Autowired
	private Services service;

	/**
	 * API for the Registration
	 * 
	 * @param information
	 * @return Status and the Body
	 */

	@PostMapping("/user/registration")

	public ResponseEntity<Response> registration(@RequestBody UserDto information) {

		boolean value = service.register(information);
		if (value) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("Registration Successfull", 200, information));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
				.body(new Response("User has been already Registered", 208, information));
	}

	/**
	 * API for the Verify the Token
	 * 
	 * @param token
	 * @return Body and Status
	 * @throws Exception
	 */
	@GetMapping("users/verify/{token}")
	public ResponseEntity<Response> userVerfication(@PathVariable("token") String token) throws Exception {
		boolean update = service.verify(token);
		if (update) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response(token, 200, "Token Has Been Verified"));

		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response(token, 401, "Not Verified"));
	}

	/**
	 * API for the Login
	 * 
	 * @param information
	 * @return Status and Body
	 */
	@PostMapping("user/login")
	public ResponseEntity<UserDetail> login(@RequestBody LoginInformation information) {
		UserInformation userInformation = service.login(information);
		if (userInformation != null) {
			// String token = generate.JwtToken(userInformation.getUserId());
			/*
			 * Token must be added in login
			 */
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("login successfull", information.getEmail())
					.body(new UserDetail("Login Sucessfull", 200, information));
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserDetail("Login is failed", 400, information));

	}

	/**
	 * API for the Forgot Password
	 * 
	 * @param email
	 * @return Respective Status and The Body
	 */

	@PostMapping("/user/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam("email") String email) {

		boolean result = service.isUserExist(email);
		if (result) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("User Exists", 200, email));

		}
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new Response("User Does not exit in the given email id", 400, email));

	}

	/**
	 * API for the update the Detail
	 * 
	 * @param token
	 * @param update
	 * @return Status and Body
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

	/**
	 * API to get all the Details
	 * 
	 * @return status and body
	 */

	@GetMapping("user/getusers")
	public ResponseEntity<Response> getUsers() {
		List<UserInformation> users = service.getUsers();

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("The Registered user are", 200, users));

	}

	/**
	 * API Used to get the single users
	 * 
	 * @param token
	 * @return Status and Body
	 */
	@GetMapping("user/getsingleusers")
	public ResponseEntity<Response> getOneUser(@RequestHeader("token") String token) {
		UserInformation user = service.getsingleUser(token);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("The User Details", 200, user));
	}

	/**
	 * API used for the add the Collaborator
	 * 
	 * @param noteId
	 * @param email
	 * @param token
	 * @return status and Body
	 */

	@PostMapping("user/addCollaborator")
	public ResponseEntity<Response> addCollaborator(@RequestParam("noteId") Long noteId,
			@RequestParam("email") String email, @RequestHeader("token") String token) {

		NoteInformation note = service.addCollaborator(noteId, email, token);

		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("collaborator added", 200, note));

	}

	/**
	 * API used for remove the Collaborator
	 * 
	 * @param noteId
	 * @param email
	 * @param token
	 * @return Status and Body
	 */

	@DeleteMapping("user/removeCollaborator")
	public ResponseEntity<Response> removecollaborator(@RequestParam("noteId") Long noteId,
			@RequestParam("email") String email, @RequestHeader("token") String token) {
		service.removecollaborator(noteId, email, token);

		return ResponseEntity.status(HttpStatus.OK).body(new Response("Collaborator has been Removed", 200));
	}

	/**
	 * API used to display the Collaborator
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("user/getCollaborator")
	public ResponseEntity<Response> getCollaborator(@RequestHeader("token") String token) {
		List<NoteInformation> note = service.getcollaborator(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("The respective notes are", 200, note));
	}
}
