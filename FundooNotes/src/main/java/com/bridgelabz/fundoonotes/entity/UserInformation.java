package com.bridgelabz.fundoonotes.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
/**
 * 
 * @author Srijan Kumar
 *
 */
@Data
@Entity
@Table(name = "usersdetail")
public class UserInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	private String name;
	@NotBlank(message = "Password is mandatory")
	private String password;

	
	@NotNull
	private String email;
	private long mobilenumber;
	private LocalDateTime createDate;
	@Column(columnDefinition = "boolean Default false",nullable=false)
	private boolean is_verified;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	
	private List<NoteInformation> note;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Collaborator", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "note_id") })
	
	@JsonManagedReference
	@JsonIgnore
	private List<NoteInformation> colaborateNote;
	



}
