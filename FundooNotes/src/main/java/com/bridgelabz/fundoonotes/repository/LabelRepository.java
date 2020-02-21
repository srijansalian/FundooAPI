package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;

public interface LabelRepository {
	
	public LabelInformation save(LabelInformation labelInformation);
	
	public NoteInformation saveNote(NoteInformation noteInformation);
	
	public LabelInformation fetchLabel(Long userid, String labelname);

}
