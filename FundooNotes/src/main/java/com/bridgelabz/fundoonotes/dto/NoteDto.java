package com.bridgelabz.fundoonotes.dto;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author Srijan Kumar
 *
 */
public class NoteDto {
	@NotNull
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotNull
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
