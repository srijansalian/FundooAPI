package com.bridgelabz.fundoonotes.utility;

import org.springframework.stereotype.Component;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * @author Srijan Kumar
 *
 */
@Component
public class MailServiceProvider {
	/*
	 * Method that is used to Authenticate and send the mail
	 */

	public static void sendEmail(String toEmail, String subject, String body) {

		String fromEmail = System.getenv("Email");
		System.out.println(fromEmail);
		String password = System.getenv("Password");
		System.out.println(password);
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		Authenticator auth = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}

		};
		Session session = Session.getInstance(prop, auth);
		send(session, fromEmail, toEmail, subject, body);
	}

	/*
	 * Send method is used to dispatch the mail
	 */
	private static void send(Session session, String fromEmail, String toEmail, String subject, String body) {
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail, "srijan"));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject(subject);
			message.setText(body);
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

}
