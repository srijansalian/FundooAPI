package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.dto.LabelUpdate;

public interface LabelService {

	void createLabel(LabelDto label, String token);

	void createMap(LabelDto label, String token, Long noteId);

	void addlabel(Long noteId, String token, Long labelId);

	void removelabel(Long noteId, String token, Long labelId);

	void update(LabelUpdate label, String token);

}
