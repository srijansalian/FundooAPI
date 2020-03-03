package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.entity.Profile;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.ProfilePicService;

@RestController
public class ProfilePicController {

	@Autowired
	private ProfilePicService profile;

	@PostMapping("users/uploadprofilepicture")
	public ResponseEntity<Response> addProfilePic(@ModelAttribute MultipartFile file,
			@RequestHeader("token") String token) {

		Profile profilepic = profile.storeObjectInS3(file, file.getOriginalFilename(), file.getContentType(), token);
		return profilepic.getUserLabel() != null
				? ResponseEntity.status(HttpStatus.OK)
						.body(new Response("profile added succussefully", 200, profilepic))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("something went Wrong ", 400));

	}
	
	@PutMapping("users/updateprofilepicture")
	public ResponseEntity<Response> updateProfile(@ModelAttribute MultipartFile file , @RequestHeader("token") String token){
		Profile profilepic = profile.updateObejctInS3(file, file.getOriginalFilename(), file.getContentType(),
				token);
		return profilepic.getUserLabel() != null
				? ResponseEntity.status(HttpStatus.OK)
						.body(new Response("profile added succussefully", 200, profilepic))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("something went Wrong ", 400));
	}
}
