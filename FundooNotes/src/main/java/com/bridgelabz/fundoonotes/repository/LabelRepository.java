package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
/**
 * 
 * @author Srijan Kumar
 *
 */

public interface LabelRepository {

	public LabelInformation save(LabelInformation labelInformation);

	public NoteInformation saveNote(NoteInformation noteInformation);

	public LabelInformation fetchLabel(Long userid, String labelname);

	public LabelInformation fetchLabelById(Long id);

	public int deleteLabel(Long i);
	// public List<LabelInformation> getAllLabel(Long id);

}
