package com.bridgelabz.fundoonotes.implementation;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdate;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	public void addlabel(Long noteId, String token, Long labelId) {

		NoteInformation note = noterepository.findbyId(noteId);

		LabelInformation label = labelrepo.fetchLabelById(labelId);

		// label.getList().add(note);
		note.getList().add(label);
		noterepository.save(note);
		// labelRepository.save(label);

	}

	@Transactional
	@Override
	public void removelabel(Long noteId, String token, Long labelId) {

		NoteInformation note = noterepository.findbyId(noteId);

		LabelInformation label = labelrepo.fetchLabelById(labelId);

		note.getList().remove(label);

		noterepository.save(note);

	}

	@Override
	public void createMap(LabelDto label, String token, Long noteId) {

	}

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
			System.out.println("User does not Exist");
		}
	}

	@Transactional
	@Override
	public void delete(LabelUpdate label, String token) {

		try {
			Long id = (long) tokenGenrator.parseJWT(token);
			UserInformation user = userrepository.getUserById(id);
			if (user != null) {

				// LabelInformation lableinformation =
				// labelrepo.fetchLabelById(label.getLabelId());
				LabelInformation lableinformation = labelRepository.fetchbyId(label.getLabelId());
				if (lableinformation != null) {
					// labelrepo.deleteLabel(label.getLabelId());
					System.out.println(label.getLabelId());
					labelRepository.deletebyId(label.getLabelId());

				} else {
					throw new UserException("Not Found");
				}

			}

		} catch (Exception e) {
			System.out.println("Not Possible");
		}

	}

	/*
	 * @Transactional
	 * 
	 * @Override public List<LabelInformation> getLabel(String token) { try { Long
	 * id = (long) tokenGenrator.parseJWT(token); UserInformation user =
	 * userrepository.getUserById(id); if (user != null) { List<LabelInformation>
	 * list = labelrepo.getAllLabel(id); System.out.println(list); return list;
	 * 
	 * } else { throw new UserException("User not possiable"); }
	 * 
	 * } catch (Exception e) { throw new UserException("Not Possible"); }
	 * 
	 * }
	 */

	@Transactional
	@Override
	public List<LabelInformation> getLabel(String token) {

		Long id;
		try {
			id = (long) tokenGenrator.parseJWT(token);
		} catch (Exception e) {

			throw new UserException("note does not exist");
		}

		List<LabelInformation> labels = labelRepository.fetchlabelbyId(id);
		return labels;

	}
}
