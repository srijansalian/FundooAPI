package com.bridgelabz.fundoonotes.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 
 * @author Srijan Kumar
 *
 */
@Configuration
public class ApplicationConfiguration {
	
/**
 * Bean for the password
 * @return BryptPasswordEncode
 *  */
	@Bean
	public BCryptPasswordEncoder getpasswordEncryption() {
		return new BCryptPasswordEncoder();
	}
/**
 * Bean for the ModelMapper
 * @return  ModelMapper
 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
