package com.bridgelabz.fundoonotes.implementation;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LoginInformation;
import com.bridgelabz.fundoonotes.dto.PasswordUpdate;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.MailObject;
import com.bridgelabz.fundoonotes.response.MailResponse;
import com.bridgelabz.fundoonotes.service.Services;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;
import com.bridgelabz.fundoonotes.utility.MailServiceProvider;

/**
 * 
 * @author Srijan Kumar
 *
 */
/*
 * Implementation for the service declaration
 */
@Service
public class ServiceImplementation implements Services {
	private UserInformation userInformation = new UserInformation();

	@Autowired
	private UserRepository repository;
	@Autowired
	private JwtGenerator generate;
	@Autowired
	private BCryptPasswordEncoder encryption;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private MailResponse response;
	@Autowired
	private MailObject mailObject;

	/*
	 * Method for the Registration
	 */
	
	@Transactional
	@Override
	public boolean register(UserDto information) {
		UserInformation user = repository.getUser(information.getEmail());
		if (user == null) {
			userInformation = modelMapper.map(information, UserInformation.class);
			userInformation.setCreateDate(LocalDateTime.now());
			String epassword = encryption.encode(information.getPassword());
			userInformation.setPassword(epassword);
			userInformation.set_verified(false);
			userInformation = repository.save(userInformation);
			String mailResponse = response.fromMessage("http://localhost:8080/verify",
					generate.JwtToken(userInformation.getUserId()));

			mailObject.setEmail(information.getEmail());
			mailObject.setMessage(mailResponse);
			mailObject.setSubject("verification");
			MailServiceProvider.sendEmail(mailObject.getEmail(), mailObject.getSubject(), mailObject.getMessage());

			return true;
		}
		// throw new UserException("user already exists with the same mail id");
		return false;
	}

	@Transactional
	@Override
	public UserInformation login(LoginInformation information) {
		UserInformation user = repository.getUser(information.getEmail());
		if (user != null) {
			if ((user.is_verified() == true) && (encryption.matches(information.getPassword(), user.getPassword()))) {
				System.out.println(generate.JwtToken(user.getUserId()));
				return user;
			} else {
				String mailResponse = response.fromMessage("http://localhost:8080/verify",
						generate.JwtToken(user.getUserId()));
				MailServiceProvider.sendEmail(information.getEmail(), "Verification", mailResponse);
				return null;
			}
		} else {
			return null;
		}

	}

	/*
	 * Controller method for the verify
	 */
	@Transactional
	@Override
	public boolean verify(String token) throws Exception {
		System.out.println("id in verification" + (long) generate.parseJWT(token));
		Long id = (long) generate.parseJWT(token);
		repository.verify(id);
		return true;
	}
/*
 * Used for the check the the user is present in database or not 
 */
	
	@Override
	public boolean isUserExist(String email) {
		try {
			UserInformation user = repository.getUser(email);
			if (user.is_verified() == true) {
				String mailResponse = response.fromMessage("http://localhost:8080/verify",
						generate.JwtToken(user.getUserId()));
				MailServiceProvider.sendEmail(user.getEmail(), "Verification", mailResponse);
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			throw new UserException("User does not exit");
		}

	}
	/*
	 * Used to update the password of the user
	 */
	@Transactional
	@Override
	public boolean update(PasswordUpdate information, String token) {
		Long id = null;
		System.out.println("hello");
		try {
		id =(Long) generate.parseJWT(token);
		String epassword = encryption.encode(information.getConfirmPassword());
		information.setConfirmPassword(epassword);
		return repository.upDate(information, id);
		
		}catch(Exception e) {
			e.printStackTrace();
			throw new UserException("Invalid Input");
		}
	}
	/*
	 * Used to get all the details from the Databases
	 */
	@Transactional
	@Override
	public List<UserInformation> getUsers() {
		List<UserInformation> users = repository.getUsers();
		//UserInformation user = users.get(0);
		return users;
	}
	/*
	 * Used to get the Single details from the Databases
	 */
	@Transactional
	@Override
	public UserInformation getsingleUser(String token) {
		Long id;
		try {
			id = (Long) generate.parseJWT(token);

		} catch (Exception e) {
			throw new UserException("user does not exist");

		}
		UserInformation user = repository.getUserById(id);

		return user;
		
	}

}
