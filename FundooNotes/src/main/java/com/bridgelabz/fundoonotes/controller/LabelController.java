package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.LabelService;

/**
 * 
 * @author Srijan Kumar
 *
 */
@RestController
public class LabelController {

	@Autowired
	private LabelService service;

	/**
	 * API for create the Label
	 * 
	 * @param label
	 * @param token
	 * @return status and the Body
	 */

	@PostMapping("/label/create")
	public ResponseEntity<Response> create(@RequestBody LabelDto label, @RequestHeader("token") String token) {
		service.createLabel(label, token);

		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Label is Created", 200, label));

	}

	/*
	 * @PostMapping("/label/createAndMapping") public ResponseEntity<Response>
	 * createandmap (@RequestBody LabelDto label , @RequestHeader("token")String
	 * token , @RequestParam("noteId")Long noteId){
	 * service.createMap(label,token,noteId);
	 * 
	 * return null;
	 * 
	 * }
	 */

	/**
	 * API for the Add the label to the Notes
	 * 
	 * @param noteId
	 * @param token
	 * @param labelId
	 * @return Body and Status
	 */

	@PostMapping("/label/addlabel")
	public ResponseEntity<Response> addlabel(@RequestParam("noteId") Long noteId, @RequestHeader("token") String token,
			@RequestParam("labelId") Long labelId) {
		service.addlabel(noteId, token, labelId);

		return ResponseEntity.status(HttpStatus.OK).body(new Response("label has been added to an Note", 200, labelId));

	}
	
	@PostMapping("/label/removelabel")
	public ResponseEntity<Response> removelabel(@RequestParam("noteId") Long noteId, @RequestHeader("token") String token,
			@RequestParam("labelId") Long labelId) {
		service.removelabel(noteId, token, labelId);
		return null;
	}


}
