package com.bridgelabz.fundoonotes.implementation;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.amazonaws.services.xray.model.Http;
import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdate;
import com.bridgelabz.fundoonotes.dto.ReminderDto;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.exception.NoteException;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.ElasticSearch;
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

	@Autowired
	private ElasticSearch elasticsearch;

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
				noteInformation.setColour("white");

				user.getNote().add(noteInformation);
				NoteInformation note = noteRepository.save(noteInformation);
				/*
				 * if (note != null) {
				 * 
				 * try {
				 * 
				 * elasticsearch.CreateNote(noteInformation);
				 * 
				 * } catch (Exception e) { throw new
				 * NoteException("Not possiable to create",HttpStatus.NOT_ACCEPTABLE); } }
				 */

			}

		} catch (Exception e) {
			
			throw new UserException("User does not Register !!!!!!!!",HttpStatus.NOT_ACCEPTABLE);
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

			user = repository.getUserById(userid);

			NoteInformation noteinfo = noteRepository.findbyId(userid);
			System.out.println(noteinfo);
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
			throw new UserException("User does not register",HttpStatus.NOT_FOUND);
			
		}

	}

	/**
	 * Used for the pin of an Note
	 */
	@Transactional
	@Override
	public void pinNote(Long id, String token) {
		try {
			Long userid = (Long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userid);
			NoteInformation info = noteRepository.findbyId(userid);
			if (info != null) {
				info.setArchieved(false);
				info.setPinned(!info.isPinned());
				noteRepository.save(info);
			}
		} catch (Exception e) {
			throw new UserException("User is not register",HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Used for the archive the Note
	 */

	@Transactional
	@Override
	public void archieve(Long id, String token) {
		try {
			Long userid = (Long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userid);
			NoteInformation info = noteRepository.findbyId(userid);
			if (info != null) {
				info.setPinned(false);
				info.setArchieved(!info.isArchieved());
				noteRepository.save(info);
			}
		} catch (Exception e) {
			throw new UserException("User does not exit",HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * Used to move the file into trash
	 */
	@Transactional
	@Override
	public void deleteNote(long id, String token) {
		NoteInformation info = noteRepository.findbyId(id);
		info.setTrashed(!info.isTrashed());
		noteRepository.save(info);

	}

	/**
	 * Delete Note permanently
	 */
	@Transactional
	@Override
	public boolean deletepermantely(long id, String token) {

		try {
			Long userid = (Long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userid);
			NoteInformation info = noteRepository.findbyId(id);

			if (info != null) {
				System.out.println(info.getId());
				noteRepository.deleteNode(id, userid);
				System.out.println(info.getId());
				elasticsearch.DeleteNote(info);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("user does not exist",HttpStatus.NOT_FOUND);

		}
		return false;

	}

	/**
	 * Used to get all the notes from the user
	 */
	@Transactional
	@Override
	public List<NoteInformation> getallnotes(String token) {

		try {
			Long userId = (long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userId);

			if (user != null) {
				List<NoteInformation> list2 = noteRepository.getNotes(userId);
				System.out.println(list2);
				return list2;
			} else {
				throw new NoteException("note is not present",HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			throw new UserException("User is not found",HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Used to get all the Trashed Notes from the user
	 */
	@Transactional
	@Override
	public List<NoteInformation> getTrashedNotes(String token) {
		try {
			Long userId = (long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userId);

			if (user != null) {
				List<NoteInformation> list3 = noteRepository.getTrashedNotes(userId);
				System.out.println(list3);
				return list3;
			} else {
				throw new NoteException("Note is not present",HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			throw new UserException("User is not found",HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * Get all the Archived from the Notes
	 */
	@Transactional
	@Override
	public List<NoteInformation> getArchivedNotes(String token) {
		try {
			Long userId = (long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userId);

			if (user != null) {
				List<NoteInformation> list4 = noteRepository.getArchivednote(userId);
				System.out.println(list4);
				return list4;
			} else {
				throw new NoteException("Note is not present",HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			throw new UserException("User is not found",HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * API used to Fetch the Pinned Notes
	 */
	@Transactional
	@Override
	public List<NoteInformation> getPinnedNotes(String token) {
		try {
			Long userId = (long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userId);

			if (user != null) {
				List<NoteInformation> list5 = noteRepository.getPinnedNotes(userId);
				System.out.println(list5);
				return list5;
			} else {
				throw new NoteException("Note is not present",HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			throw new UserException("User is not found",HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * Used to add the Color to the an Note
	 */
	@Transactional
	@Override
	public void addcolour(Long noteId, String colour, String token) {

		try {
			Long userid = (Long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userid);
			NoteInformation info = noteRepository.findbyId(noteId);
			info.setColour(colour);
			noteRepository.save(info);

		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("user does not exist",HttpStatus.NOT_FOUND);

		}

	}

	/**
	 * Used to Add the Remainder
	 */
	@Transactional
	@Override
	public void addReminder(Long noteId, String token, ReminderDto reminder) {

		try {
			Long userid = (Long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userid);
			NoteInformation info = noteRepository.findbyId(noteId);
			if (user != null) {
				info.setRemainder(reminder.getReminder());
				noteRepository.save(info);

			} else {
				throw new NoteException("Note not found",HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			throw new UserException("User Does not exist",HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * Used to remove The Remainder
	 */

	@Transactional
	@Override
	public void removeReminder(Long noteId, String token) {
		try {
			Long userid = (Long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userid);
			NoteInformation info = noteRepository.findbyId(noteId);
			if (user != null) {
				info.setRemainder(null);
				noteRepository.save(info);

			} else {
				throw new NoteException("Note is not found",HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			throw new UserException("user not exist",HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public List<NoteInformation> searchByTitle(String title) {
		List<NoteInformation> notes = elasticsearch.searchbytitle(title);
		if (notes != null) {
			return notes;
		} else {
			return null;
		}

	}

}
