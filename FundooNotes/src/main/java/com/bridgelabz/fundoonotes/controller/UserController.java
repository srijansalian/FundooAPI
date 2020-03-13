package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundoonotes.dto.LoginInformation;
import com.bridgelabz.fundoonotes.dto.PasswordUpdate;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.Profile;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.UserDetail;
import com.bridgelabz.fundoonotes.service.ProfilePicService;
import com.bridgelabz.fundoonotes.service.UserServices;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

/**
 * 
 * @author Srijan Kumar
 *
 */
@RestController

public class UserController {

	@Autowired
	private UserServices service;

	@Autowired
	private ProfilePicService profile;
	
	@Autowired
	private JwtGenerator generate;

	/**
	 * API for the Registration
	 * 
	 * @param information
	 * @return Status and the Body
	 */

	@PostMapping("/users/register")

	public ResponseEntity<Response> registration(@RequestBody UserDto information) {

		boolean value = service.register(information);
		if (value) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("Registration Successfull", information));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
				.body(new Response("User has been already Registered"));
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
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response(token, "Token Has Been Verified"));

		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Not Verified"));
	}

	/**
	 * API for the Login
	 * 
	 * @param information
	 * @return Status and Body
	 */
	@PostMapping("users/login")
	public ResponseEntity<UserDetail> login(@RequestBody LoginInformation information) {
		UserInformation userInformation = service.login(information);
		if (userInformation != null) {
			// String token = generate.JwtToken(userInformation.getUserId());
			/*
			 * Token must be added in login
			 */
			String token = generate.JwtToken(userInformation.getUserId());
			System.out.println(token);
			return ResponseEntity.status(HttpStatus.OK).header("login successfull", information.getEmail())
					.body(new UserDetail(token,200,information));
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserDetail("Login is failed",400));

	}

	/**
	 * API for the Forgot Password
	 * 
	 * @param email
	 * @return Respective Status and The Body
	 */

	@PostMapping("/users/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestBody LoginInformation info) {

		boolean result = service.isUserExist(info.getEmail());
		if (result) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("User Exists", info.getEmail()));

		}
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new Response("User Does not exit in the given email id", info.getEmail()));

	}

	/**
	 * API for the update the Detail
	 * 
	 * @param token
	 * @param update
	 * @return Status and Body
	 */

	@PutMapping("/users/update/{token}")
	public ResponseEntity<Response> update(@PathVariable("token") String token, @RequestBody PasswordUpdate update) {
		boolean result = service.update(update, token);
		if (result) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new Response("password updated successfully", update));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("password  does not match"));

	}

	/**
	 * API to get all the Details
	 * 
	 * @return status and body
	 */

	@GetMapping("users/getusers")
	public ResponseEntity<Response> getUsers() {
		List<UserInformation> users = service.getUsers();

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("The Registered user are", users));

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
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("The User Details", user));
	}

	/**
	 * API used for the add the Collaborator
	 * 
	 * @param noteId
	 * @param email
	 * @param token
	 * @return status and Body
	 */

	@PostMapping("users/addCollaborator")
	public ResponseEntity<Response> addCollaborator(@RequestParam("noteId") Long noteId,
			@RequestParam("email") String email, @RequestHeader("token") String token) {

		NoteInformation note = service.addCollaborator(noteId, email, token);

		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("collaborator added", note));

	}

	/**
	 * API used for remove the Collaborator
	 * 
	 * @param noteId
	 * @param email
	 * @param token
	 * @return Status and Body
	 */

	@DeleteMapping("users/removeCollaborator")
	public ResponseEntity<Response> removecollaborator(@RequestParam("noteId") Long noteId,
			@RequestParam("email") String email, @RequestHeader("token") String token) {
		service.removecollaborator(noteId, email, token);

		return ResponseEntity.status(HttpStatus.OK).body(new Response("Collaborator has been Removed"));
	}

	/**
	 * API used to display the Collaborator
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("users/getCollaborator")
	public ResponseEntity<Response> getCollaborator(@RequestHeader("token") String token) {
		List<NoteInformation> note = service.getcollaborator(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("The respective notes are", note));
	}

	/**
	 * API for the upload the profile picture
	 * 
	 * @param file
	 * @param token
	 * @return Status
	 */
	@PostMapping("users/uploadprofilepicture")
	public ResponseEntity<Response> addProfilePic(@ModelAttribute MultipartFile file,
			@RequestHeader("token") String token) {

		Profile profilepic = profile.storeObjectInS3(file, file.getOriginalFilename(), file.getContentType(), token);
		return profilepic.getUserLabel() != null
				? ResponseEntity.status(HttpStatus.OK).body(new Response("profile added succussefully", profilepic))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Something went Wrong "));

	}

	/**
	 * API for the update the profile picture
	 * 
	 * @param file
	 * @param token
	 * @return Status and Body
	 */
	@PutMapping("users/updateprofilepicture")
	public ResponseEntity<Response> updateProfile(@ModelAttribute MultipartFile file,
			@RequestHeader("token") String token) {
		Profile profilepic = profile.updateObejctInS3(file, file.getOriginalFilename(), file.getContentType(), token);
		return profilepic.getUserLabel() != null
				? ResponseEntity.status(HttpStatus.OK).body(new Response("profile Has Been Updated", profilepic))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("something went Wrong "));
	}

	/**
	 * API for the Get the the profile picture
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/users/getProfilePicture")
	public ResponseEntity<Response> getProfilePic(@RequestHeader("token") String token) {

		S3Object s3 = profile.getProfilePic(token);
		return s3 != null ? ResponseEntity.status(HttpStatus.OK).body(new Response("The Details Are", s3))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("no profile pic "));
	}
}
