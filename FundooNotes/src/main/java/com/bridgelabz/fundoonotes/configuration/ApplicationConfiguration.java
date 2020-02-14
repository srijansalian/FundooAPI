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
	
/*
 * Returns the BCryptPasswordEncode
 */
	@Bean
	public BCryptPasswordEncoder getpasswordEncryption() {
		return new BCryptPasswordEncoder();
	}
/*
 * Return Model Mappers
 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
