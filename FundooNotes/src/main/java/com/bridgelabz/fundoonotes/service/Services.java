package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.LoginInformation;
import com.bridgelabz.fundoonotes.dto.PasswordUpdate;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.UserInformation;

/**
 * 
 * @author Srijan Kumar Service Declaration class
 */
public interface Services {
	boolean register(UserDto information);

	UserInformation login(LoginInformation information);

	boolean verify(String token) throws Exception;

	boolean isUserExist(String email);

	boolean update(PasswordUpdate information, String token);

	List<UserInformation> getUsers();

	UserInformation getsingleUser(String token);

	NoteInformation addCollaborator(Long noteId, String email, String token);

	void removecollaborator(Long noteId, String email, String token);

	List<NoteInformation> getcollaborator(String token);

}
