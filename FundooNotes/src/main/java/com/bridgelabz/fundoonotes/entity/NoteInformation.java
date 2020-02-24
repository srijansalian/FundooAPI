package com.bridgelabz.fundoonotes.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;
import lombok.Data;

/**
 * 
 * @author Srijan Kumar
 *
 */
@Data
@Entity
@Table(name = "Note_Info")
public class NoteInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String description;
	private boolean isArchieved;
	private boolean isPinned;
	private boolean isTrashed;
	private LocalDateTime createdDateAndTime;
	private LocalDateTime upDateAndTime;
	private String colour;
	private LocalDateTime remainder;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Label_Note", joinColumns = { @JoinColumn(name = "note_id") }, inverseJoinColumns = {
			@JoinColumn(name = "label_id") })
	@JsonBackReference
	@JsonIgnore
	private List<LabelInformation> list;

	

}
