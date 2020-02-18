package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdate;

public interface NoteService {
	
	public void createNote(NoteDto information,String token);
	
	public void noteUpdate(NoteUpdate information,String token);
	
	public void pinNote(Long id ,String token);

	public void archieve(Long id, String token);

}
