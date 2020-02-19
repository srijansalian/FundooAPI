package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdate;
import com.bridgelabz.fundoonotes.dto.ReminderDto;
import com.bridgelabz.fundoonotes.entity.NoteInformation;

public interface NoteService {
	
	public void createNote(NoteDto information,String token);
	
	public void noteUpdate(NoteUpdate information,String token);
	
	public void pinNote(Long id ,String token);

	public void archieve(Long id, String token);

	public void deleteNote(long id, String token);

	public boolean deletepermantely(long id, String token);

	public List<NoteInformation> getallnotes(String token);

	public List<NoteInformation> getTrashedNotes(String token);

	public List<NoteInformation> getArchivedNotes(String token);

	public List<NoteInformation> getPinnedNotes(String token);

	public void addcolour(Long noteId, String colour, String token);

	public void addReminder(Long noteId, String token, ReminderDto reminder);

	public void removeReminder(Long noteId, String token);

	

}
