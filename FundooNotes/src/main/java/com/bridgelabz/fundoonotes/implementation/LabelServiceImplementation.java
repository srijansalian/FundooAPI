package com.bridgelabz.fundoonotes.implementation;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.amazonaws.services.xray.model.Http;
import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdate;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.exception.LabelException;

import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.repository.LabelRepo;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.LabelService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

/**
 * 
 * @author Srijan Kumar
 *
 */
@Service
public class LabelServiceImplementation implements LabelService {

	@Autowired
	private LabelRepository repository;

	private LabelInformation labelInformation = new LabelInformation();

	@Autowired
	private UserRepository userrepository;

	@Autowired
	private JwtGenerator tokenGenrator;

	@Autowired
	private NoteRepository noterepository;

	@Autowired
	private LabelRepo labelRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private LabelRepository labelrepo;

	/**
	 * Used to create the label of an given token
	 */
	@Transactional
	@Override
	public void createLabel(LabelDto label, String token) {

		try {
			Long id = (long) tokenGenrator.parseJWT(token);
			UserInformation user = userrepository.getUserById(id);
			if (user != null) {
				LabelInformation lableinfo = labelRepository.fetchlabel(user.getUserId(), label.getName());
				if (lableinfo == null) {
					labelInformation = modelMapper.map(label, LabelInformation.class);

					labelInformation.getLabelId();
					labelInformation.getName();
					labelInformation.setUserId(user.getUserId());
					repository.save(labelInformation);
				} else {
					throw new LabelException("label with the given name is already present",HttpStatus.NOT_FOUND);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("Note does not exist with the given id",HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Used to add the label into an note
	 */

	@Transactional
	@Override
	public void addlabel(Long noteId, String token, Long labelId) {

		NoteInformation note = noterepository.findbyId(noteId);

		LabelInformation label = labelrepo.fetchLabelById(labelId);

		note.getList().add(label);
		noterepository.save(note);

	}

	/**
	 * Used to Remove the label into an note
	 */

	@Transactional
	@Override
	public void removelabel(Long noteId, String token, Long labelId) {

		NoteInformation note = noterepository.findbyId(noteId);

		LabelInformation label = labelrepo.fetchLabelById(labelId);

		note.getList().remove(label);

		noterepository.save(note);

	}

	/**
	 * Used to update the label of an given token
	 */
	@Transactional
	@Override
	public void update(LabelUpdate label, String token) {

		try {
			Long id = (long) tokenGenrator.parseJWT(token);
			UserInformation user = userrepository.getUserById(id);
			if (user != null) {

				LabelInformation lableinformation = labelRepository.fetchbyId(label.getLabelId());
				if (lableinformation != null) {
					lableinformation.setName(label.getLabelName());
					labelrepo.save(lableinformation);

				}
			}
		} catch (Exception e) {
			throw new UserException("User does not Exist",HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Used to Delete the label from an user
	 */

	@Transactional
	@Override
	public void delete(LabelUpdate label, String token) {

		try {
			Long id = (long) tokenGenrator.parseJWT(token);
			UserInformation user = userrepository.getUserById(id);
			if (user != null) {

				LabelInformation lableinformation = labelRepository.fetchbyId(label.getLabelId());
				if (lableinformation != null) {
					// labelrepo.deleteLabel(label.getLabelId());
					System.out.println(label.getLabelId());
					labelRepository.deletebyId(label.getLabelId());

				} else {
					throw new LabelException("Label Not Found",HttpStatus.NOT_FOUND);
				}

			}

		} catch (Exception e) {
			throw new UserException("User not found",HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * Used to get the all the label
	 */

	@Transactional
	@Override
	public List<LabelInformation> getLabel(String token) {

		Long id;
		try {
			id = (long) tokenGenrator.parseJWT(token);
		} catch (Exception e) {

			throw new UserException("note does not exist",HttpStatus.NOT_FOUND);
		}

		List<LabelInformation> labels = labelRepository.fetchlabelbyId(id);
		return labels;

	}

	/**
	 * Used to create and map to the given token
	 */

	@Transactional
	@Override
	public void createAndMap(LabelDto label, String token, Long noteId) {

		try {
			Long id = (long) tokenGenrator.parseJWT(token);
			UserInformation user = userrepository.getUserById(id);
			if (user != null) {
				LabelInformation lableinfo = labelRepository.fetchlabel(user.getUserId(), label.getName());
				if (lableinfo == null) {
					labelInformation = modelMapper.map(label, LabelInformation.class);
					labelInformation.getLabelId();
					labelInformation.getName();
					labelInformation.setUserId(user.getUserId());
					repository.save(labelInformation);
					NoteInformation note = noterepository.findbyId(noteId);
					note.getList().add(labelInformation);
					noterepository.save(note);

				}

			}
		} catch (Exception e) {
			throw new UserException("Not Possiable",HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void createMap(LabelDto label, String token, Long noteId) {

	}
}
