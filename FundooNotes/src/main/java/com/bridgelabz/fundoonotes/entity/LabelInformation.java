package com.bridgelabz.fundoonotes.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
public class LabelInformation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int labelId;
	private String name;
	private long userId;

	@ManyToMany(cascade = CascadeType.ALL)

	@JoinTable(name = "Label_Note", joinColumns = { @JoinColumn(name = "label_id") }, inverseJoinColumns = {

			@JoinColumn(name = "note_id") })

	@JsonBackReference
	private List<NoteInformation> list;

}
