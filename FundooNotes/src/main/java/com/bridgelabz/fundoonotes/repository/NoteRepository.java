package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.dto.NoteUpdate;
import com.bridgelabz.fundoonotes.entity.NoteInformation;

/**
 * 
 * @author Srijan Kumar
 *
 */
public interface NoteRepository {

	NoteInformation save(NoteInformation noteInformation);

	NoteInformation findbyId(Long id);

	boolean deleteNode(Long id , long userid);

}
