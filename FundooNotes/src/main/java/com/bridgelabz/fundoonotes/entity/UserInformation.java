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

//	
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	
//	
//	
//	public boolean isIs_verified() {
//		return is_verified;
//	}
//
//	public void setIs_verified(boolean is_verified) {
//		this.is_verified = is_verified;
//	}
//
//	
//
//	public long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(long userId) {
//		this.userId = userId;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public long getMobilenumber() {
//		return mobilenumber;
//	}
//
//	public void setMobilenumber(long mobilenumber) {
//		this.mobilenumber = mobilenumber;
//	}
//
//
//	public LocalDateTime getCreateDate() {
//		return createDate;
//	}
//
//	public void setCreateDate(LocalDateTime createDate) {
//		this.createDate = createDate;
//
//		
//	}
//	
//	public List<NoteInformation> getNote() {
//		return note;
//	}
//
//	public void setNote(List<NoteInformation> note) {
//		this.note = note;
//	}

}
