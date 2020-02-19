package com.bridgelabz.fundoonotes.dto;

import lombok.Data;

/**
 * 
 * @author Srijan Kumar
 *
 */

@Data
public class LabelUpdate {
	private Long labelId;
	
	private String labelName;

	public Long getLabelId() {
		return labelId;
	}

	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

}
