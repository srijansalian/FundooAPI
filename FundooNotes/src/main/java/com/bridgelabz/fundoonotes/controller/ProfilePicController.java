package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundoonotes.entity.Profile;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.ProfilePicService;
/**
 * 
 * @author Srijan Kumar
 *
 */
@RestController
public class ProfilePicController {

	@Autowired
	private ProfilePicService profile;
/**
 * API for the upload the profile picture
 * @param file
 * @param token
 * @return Status
 */
	@PostMapping("users/uploadprofilepicture")
	public ResponseEntity<Response> addProfilePic(@ModelAttribute MultipartFile file,
			@RequestHeader("token") String token) {

		Profile profilepic = profile.storeObjectInS3(file, file.getOriginalFilename(), file.getContentType(), token);
		return profilepic.getUserLabel() != null
				? ResponseEntity.status(HttpStatus.OK)
						.body(new Response("profile added succussefully", 200, profilepic))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("something went Wrong ", 400));

	}
	/**
	 * API for the update the profile picture
	 * @param file
	 * @param token
	 * @return Status and Body
	 */
	@PutMapping("users/updateprofilepicture")
	public ResponseEntity<Response> updateProfile(@ModelAttribute MultipartFile file , @RequestHeader("token") String token){
		Profile profilepic = profile.updateObejctInS3(file, file.getOriginalFilename(), file.getContentType(),
				token);
		return profilepic.getUserLabel() != null
				? ResponseEntity.status(HttpStatus.OK)
						.body(new Response("profile Has Been Updated", 200, profilepic))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("something went Wrong ", 400));
	}
	
	/**
	 * API for the Get the the profile picture
	 * @param token
	 * @return
	 */
	@GetMapping("/users/getProfilePicture")
	public ResponseEntity<Response> getProfilePic(@RequestHeader("token") String token){
		
		S3Object s3 = 	profile.getProfilePic(token);
		return s3!=null ?  ResponseEntity.status(HttpStatus.OK).body(new Response("The Details Are", 200, s3))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("no profile pic ", 400));
	}
}
