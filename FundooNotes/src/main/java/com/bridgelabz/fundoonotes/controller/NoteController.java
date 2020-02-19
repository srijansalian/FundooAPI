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

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdate;
import com.bridgelabz.fundoonotes.dto.ReminderDto;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.NoteService;

/**
 * 
 * @author Srijan Kumar
 *
 */
@RestController
public class NoteController {

	@Autowired
	private NoteService service;

	/**
	 * API for create a Note
	 * 
	 * @param information
	 * @param token
	 * @return status
	 */
	@PostMapping("/note/create")
	public ResponseEntity<Response> create(@RequestBody NoteDto information, @RequestHeader("token") String token) {
		service.createNote(information, token);

		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Note Created", 200, information));

	}

	/**
	 * API for the Update Note
	 * 
	 * @param information
	 * @param token
	 * @return Status
	 */
	@PutMapping("/note/update")
	public ResponseEntity<Response> update(@RequestBody NoteUpdate information, @RequestHeader("token") String token) {
		service.noteUpdate(information, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Note Updated", 201, information));
	}

	/**
	 * API for the Pin the Note
	 * 
	 * @param id
	 * @param token
	 * @return Status
	 */
	@PutMapping("/note/pin/{id}")
	public ResponseEntity<Response> pin(@PathVariable long id, @RequestHeader("token") String token) {
		service.pinNote(id, token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Note Pinned", 201));

	}

	/**
	 * API for the achieve the Note
	 * 
	 * @param id
	 * @param token
	 * @return status
	 */

	@PutMapping("/note/archieve/{id}")
	public ResponseEntity<Response> archieve(@PathVariable long id, @RequestHeader("token") String token) {
		service.archieve(id, token);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Note Archieved", 201));

	}

	/**
	 * API for the move the note into Trash
	 * 
	 * @param id
	 * @param token
	 * @return status and the Body
	 */

	@DeleteMapping("/note/delete/{id}")
	public ResponseEntity<Response> delete(@PathVariable long id, @RequestHeader("token") String token) {
		service.deleteNote(id, token);

		return ResponseEntity.status(HttpStatus.OK).body(new Response("Note is moved to Trash", 200));

	}

	/**
	 * API for the Delete the note permanent
	 * 
	 * @param id
	 * @param token
	 * @return status and the Body
	 */

	@DeleteMapping("/note/deletepermantely/{id}")
	public ResponseEntity<Response> deletepermantely(@PathVariable long id, @RequestHeader("token") String token) {
		service.deletepermantely(id, token);

		return ResponseEntity.status(HttpStatus.OK).body(new Response("Note has been Deleted permanetly", 200));

	}

	/**
	 * API for get all the notes of an user
	 * 
	 * @param token
	 * @return status and the Body
	 */
	@GetMapping("/note/getallnotes")
	public ResponseEntity<Response> getallnotes(@RequestHeader("token") String token) {
		List<NoteInformation> note = service.getallnotes(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("The respective notes are", 200, note));

	}

	/**
	 * API for the get all the notes which are moved to the Trash
	 * 
	 * @param token
	 * @return status and body
	 */

	@GetMapping("/notes/getTrashedNotes")
	public ResponseEntity<Response> gettrashedNotes(@RequestHeader("token") String token) {
		List<NoteInformation> note1 = service.getTrashedNotes(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("The Trashed notes are", 200, note1));
	}

	/**
	 * API for get all the Archived notes of an User
	 * 
	 * @param token
	 * @return status and body
	 */
	@GetMapping("/notes/getArchiveNotes")
	public ResponseEntity<Response> getArchiveNotes(@RequestHeader("token") String token) {
		List<NoteInformation> note1 = service.getArchivedNotes(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("The Archived notes are", 200, note1));
	}

	/**
	 * API for get all the Pinned notes of an User
	 * 
	 * @param token
	 * @return status and body
	 */

	@GetMapping("/notes/getPinnedNotes")
	public ResponseEntity<Response> getPinnedNotes(@RequestHeader("token") String token) {
		List<NoteInformation> note1 = service.getPinnedNotes(token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("The Pinned notes are", 200, note1));
	}

	/**
	 * API for the Change the color of the Note
	 * 
	 * @param noteId
	 * @param colour
	 * @param token
	 * @return status and Body
	 */
	@PutMapping("/notes/setColour")
	public ResponseEntity<Response> getColour(@RequestParam("noteId") Long noteId,
			@RequestParam("colour") String colour, @RequestHeader("token") String token) {
		service.addcolour(noteId, colour, token);

		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("The Colour has been Changed", 200, colour));

	}

	/**
	 * 
	 * @param noteId
	 * @param token
	 * @param reminder
	 * @return
	 */

	@PutMapping("/notes/addRemainder")
	public ResponseEntity<Response> addRemainder(@RequestParam("noteId") Long noteId,
			@RequestHeader("token") String token, @RequestBody ReminderDto reminder) {
		service.addReminder(noteId, token, reminder);

		return ResponseEntity.status(HttpStatus.OK).body(new Response("The Remainder has benn Added", 200, reminder));

	}

}
