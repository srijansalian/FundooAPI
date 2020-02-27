package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.entity.NoteInformation;

public interface ElasticSearch {

	String CreateNote(NoteInformation noteInformation);

	String DeleteNote(NoteInformation info);

	List<NoteInformation> searchbytitle(String title);
 
	
	
}
