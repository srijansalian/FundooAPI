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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
/**
 * 
 * @author Srijan Kumar
 *
 */
/*
 * Databases connection and the other operation are all taken place
 */
@Data
@Entity
@Table(name = "usersdetail")
public class UserInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	private String name;
	private String password;
	private String email;
	private long mobilenumber;
	private LocalDateTime createDate;
	@Column(columnDefinition = "boolean Default false",nullable=false)
	private boolean is_verified;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId")
	
	private List<NoteInformation> note;



}
