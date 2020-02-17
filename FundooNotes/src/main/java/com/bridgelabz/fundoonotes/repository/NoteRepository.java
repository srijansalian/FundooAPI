package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.entity.NoteInformation;

/**
 * 
 * @author Srijan Kumar
 *
 */
public interface NoteRepository {

	NoteInformation save(NoteInformation noteinformation);

	NoteInformation findbyId(Long id);

	boolean deleteNode(Long id);

}
