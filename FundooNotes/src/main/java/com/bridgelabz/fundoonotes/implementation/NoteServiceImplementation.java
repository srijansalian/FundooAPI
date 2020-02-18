package com.bridgelabz.fundoonotes.implementation;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdate;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

/**
 * 
 * @author Srijan Kumar
 *
 */
@Service
public class NoteServiceImplementation implements NoteService {

	@Autowired
	private JwtGenerator tokenGenerator;
	@Autowired
	private UserRepository repository;
	private UserInformation user = new UserInformation();
	@Autowired
	private NoteRepository noteRepository;
	private NoteInformation noteInformation = new NoteInformation();
	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Create a Note
	 */
	@Transactional
	@Override
	public void createNote(NoteDto information, String token) {
		try {
			Long userid = (Long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userid);
			if (user != null) {
				noteInformation = modelMapper.map(information, NoteInformation.class);
				noteInformation.setCreatedDateAndTime(LocalDateTime.now());
				noteInformation.setArchieved(false);
				noteInformation.setPinned(false);
				noteInformation.setTrashed(false);
				// noteInformation.setColour("white");

				// user.getNote().add(noteInformation);
				NoteInformation note = noteRepository.save(noteInformation);
				if (note != null) {
					final String key = user.getEmail();

				}
			}

		} catch (Exception e) {
			throw new UserException("User does not Register !!!!!!!!");
		}

	}
	/*
	 * Used for an Update
	 */

	@Transactional
	@Override
	public void noteUpdate(NoteUpdate information, String token) {
		try {
			Long userid = (Long) tokenGenerator.parseJWT(token);
			// System.out.println(userid);
			user = repository.getUserById(userid);
			System.out.println(user);
			NoteInformation noteinfo = noteRepository.findbyId(userid);
			// System.out.println(noteinfo);
			if (user != null) {
				noteinfo.setId(information.getId());
				noteinfo.setDescription(information.getDescription());
				noteinfo.setTitle(information.getTitle());
				noteinfo.setArchieved(information.isArchieved());
				noteinfo.setTrashed(information.isTrashed());
				noteinfo.setPinned(information.isPinned());
				noteinfo.setUpDateAndTime(LocalDateTime.now());
				noteRepository.save(noteinfo);

			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("User does not register");
		}

	}

}
