package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.service.NoteService;

@RestController
public class NoteController {
	
	@Autowired
	private NoteService service;
	
	
	
	
	

}
