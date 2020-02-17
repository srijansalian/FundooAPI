package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.NoteService;

@RestController
public class NoteController {
	
	@Autowired
	private NoteService service;
	
	@PostMapping("/note/create")
	public ResponseEntity<Response> create(@RequestBody NoteDto information,@RequestHeader("token")String token) {
		
		
		
		return null;
		
	}
	}
	
	
	

//}
