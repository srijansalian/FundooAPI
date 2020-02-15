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
			userInformation.setVerified(false);
			userInformation = repository.save(userInformation);
			String mailResponse = response.fromMessage("http://localhost:8080/verify",generate.JwtToken(userInformation.getUserId()));

			mailObject.setEmail(information.getEmail());
			mailObject.setMessage(mailResponse);
			mailObject.setSubject("verification");
			MailServiceProvider.sendEmail(mailObject.getEmail(), mailObject.getSubject(), mailObject.getMessage());

			return true;
		} 
			//throw new UserException("user already exists with the same mail id");
		return false;
	}

@Transactional
	@Override
	public UserInformation login(LoginInformation information) {
		UserInformation user = repository.getUser(information.getEmail());
		if(user !=null) {
			if((user.isVerified() == true) && (encryption.matches(information.getPassword(),user.getPassword()))) {
			System.out.println(generate.JwtToken(user.getUserId()));
			return user;
		}else {
			String mailResponse = response.fromMessage("http://localhost:8080/verify",generate.JwtToken(user.getUserId()));
			MailServiceProvider.sendEmail(information.getEmail(),"Verification", mailResponse);
			return null;
		}
		}else {
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


	@Override
	public boolean isUserExist(String email) {
		try {
			UserInformation user = repository.getUser(email);
			if(user.isVerified() == true) {
				String mailResponse = response.fromMessage("http://localhost:8080/verify", generate.JwtToken(user.getUserId()));
			MailServiceProvider.sendEmail(user.getEmail(),"Verification", mailResponse);	
			return true;
			}
			else {
				return false;
			}
			
		}catch(Exception e) {
			throw new UserException("User does not exit");
		}
		
	}


	@Override
	public boolean update(PasswordUpdate information, String token) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<UserInformation> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public UserInformation getsingleUser(String token) {
		// TODO Auto-generated method stub
		return null;
	}



}
