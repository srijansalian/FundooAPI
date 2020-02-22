package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdate;
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

	/**
	 * API for Remove the label from the Notes
	 * 
	 * @param noteId
	 * @param token
	 * @param labelId
	 * @return status and Body
	 */

	@PutMapping("/label/removelabel")
	public ResponseEntity<Response> removelabel(@RequestParam("noteId") Long noteId,
			@RequestHeader("token") String token, @RequestParam("labelId") Long labelId) {
		service.removelabel(noteId, token, labelId);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Label has been Removed", 200, labelId));
	}

	/**
	 * API used to update the Label Name
	 * 
	 * @param label
	 * @param token
	 * @return status and Body
	 */
	@PutMapping("/label/updatelabel")
	public ResponseEntity<Response> updatelabel(@RequestBody LabelUpdate label, @RequestHeader("token") String token) {

		service.update(label, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Label has been Updated", 200, label));

	}

	/**
	 * API used to delete the Label
	 * 
	 * @param label
	 * @param token
	 * @return
	 */

	@DeleteMapping("/label/deletelabel")
	public ResponseEntity<Response> deletelabel(@RequestBody LabelUpdate label, @RequestHeader("token") String token) {
		service.delete(label, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Label has been Deleted", 200, label));

	}

}
