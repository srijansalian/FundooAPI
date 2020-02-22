package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdate;
import com.bridgelabz.fundoonotes.entity.LabelInformation;

public interface LabelService {

	void createLabel(LabelDto label, String token);

	void createMap(LabelDto label, String token, Long noteId);

	void addlabel(Long noteId, String token, Long labelId);

	void removelabel(Long noteId, String token, Long labelId);

	void update(LabelUpdate label, String token);

	void delete(LabelUpdate label, String token);

	List<LabelInformation> getLabel(String token);

	void createAndMap(LabelDto label, String token, Long noteId);

}
