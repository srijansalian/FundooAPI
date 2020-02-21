package com.bridgelabz.fundoonotes.implementation;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.repository.LabelRepo;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.LabelService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

@Service
public class LabelServiceImplementation implements LabelService {

	@Autowired
	private LabelRepository repository;

	private LabelInformation labelInformation = new LabelInformation();

	@Autowired
	private UserRepository userrepository;

	private UserInformation user = new UserInformation();

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
					throw new UserException("label with the given name is already present");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("Note does not exist with the given id");
		}
	}

	@Transactional
	@Override
	public void createMap(LabelDto label, String token, Long noteId) {
		/*
		 * try { Long id = (long) tokenGenrator.parseJWT(token); UserInformation user =
		 * userrepository.getUserById(id); if (user != null) { LabelDto lableinfo =
		 * labelRepository.fetchlabel(user.getUserId(), label.getName()); if (lableinfo
		 * == null) { labelInformation = modelMapper.map(label, LabelInformation.class);
		 * 
		 * labelInformation.setUserId(user.getUserId());
		 * labelRepository.save(labelInformation);
		 * 
		 * NoteInformation note = labelRepository.findById(noteId);
		 * 
		 * }
		 * 
		 * } }
		 */

	}

	@Transactional
	@Override
	public void addlabel(Long noteId, String token, Long labelId) {

		NoteInformation note = noterepository.findbyId(noteId);

		LabelInformation label = labelrepo.fetchLabelById(labelId);

		label.getList().add(note);

		labelRepository.save(label);

	}

	@Transactional
	@Override
	public void removelabel(Long noteId, String token, Long labelId) {

		NoteInformation note = noterepository.findbyId(noteId);

		LabelInformation label = labelrepo.fetchLabelById(labelId);

		note.getList().remove(label);

		noterepository.save(note);

	}

}
