package com.bridgelabz.fundoonotes.response;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
 * @author Srijan Kumar
 * 
 */

/*
 * Class that is used for the secure of the email data that has to be sent
 */
@Data
@Component
public class MailObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;
	private String subject;
	private String message;

}
