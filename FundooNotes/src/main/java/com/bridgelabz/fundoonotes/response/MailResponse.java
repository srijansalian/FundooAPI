package com.bridgelabz.fundoonotes.response;

import org.springframework.stereotype.Component;
/**
 * 
 * @author Srijan Kumar
 *Class for the merging of the url with the tokens
 */
@Component
	public class MailResponse {
		public String fromMessage(String url, String token) {
			return url + "/" + token;
		}
		
		public String fromMessage(String url) {
			return url;
		}

}

