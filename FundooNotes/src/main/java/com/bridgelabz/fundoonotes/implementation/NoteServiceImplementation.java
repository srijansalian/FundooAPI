package com.bridgelabz.fundoonotes.implementation;

import java.time.LocalDateTime;
import java.util.List;

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
				noteInformation.setColour("white");

				user.getNote().add(noteInformation);
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
			throw new UserException("User does not register");
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
			throw new UserException("User is not register");
		}

	}

	/**
	 * Used for the archieve the Note
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
			throw new UserException("User does not exit");
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

	@Transactional
	@Override
	public boolean deletepermantely(long id, String token) {

		try {
			Long userid = (Long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userid);
			NoteInformation info = noteRepository.findbyId(userid);

			if (info != null) {
				// System.out.println(info);
				noteRepository.deleteNode(id, userid);
			}
//			} else
//				throw new UserException("Deletion is not possiable");
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("Not possiable");

		}
		return false;

	}

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
				throw new UserException("note is not present");
			}

		} catch (Exception e) {
			throw new UserException("User is not found");
		}
	}

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
				throw new UserException("note is not present");
			}

		} catch (Exception e) {
			throw new UserException("User is not found");
		}

	}

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
				throw new UserException("note is not present");
			}

		} catch (Exception e) {
			throw new UserException("User is not found");
		}

	}

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
				throw new UserException("note is not present");
			}

		} catch (Exception e) {
			throw new UserException("User is not found");
		}

	}

	@Transactional
	@Override
	public void addcolour(Long noteId, String colour, String token) {

		try {
			Long userid = (Long) tokenGenerator.parseJWT(token);
			user = repository.getUserById(userid);
			NoteInformation info = noteRepository.findbyId(userid);
			info.setColour(colour);
			noteRepository.save(info);

		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("Not possiable");

		}

	}

}
