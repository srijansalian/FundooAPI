package com.bridgelabz.fundoonotes.implementation;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.bridgelabz.fundoonotes.entity.Profile;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.repository.ProfilePicture;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.ProfilePicService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProfilePicServiceImplementation implements ProfilePicService{
	
	@Autowired
	private ProfilePicture profilepic;
	
	@Autowired
	private UserRepository userrepository;
	
	@Autowired
	private JwtGenerator jwtgenerator;
	
	@Value("${bucket}")
	private String bucketName;

	@Autowired
	private AmazonS3 amazonS3Client;

	@Transactional
	@Override
	public Profile storeObjectInS3(MultipartFile file, String originalFilename, String contentType, String token) {
		try {
			Long id = (long) jwtgenerator.parseJWT(token);
			UserInformation user = userrepository.getUserById(id);
			
			
			if (user != null) {
				
				Profile profile = new Profile(originalFilename, user);
				ObjectMetadata objectMetadata = new ObjectMetadata();
				objectMetadata.setContentType(contentType);
				objectMetadata.setContentLength(file.getSize());
				
				amazonS3Client.putObject(bucketName, originalFilename, file.getInputStream(), objectMetadata);
				System.out.println("Hello");
				profilepic.save(profile);
				return profile;
			}
		} catch (AmazonClientException | IOException exception) {
			//throw new RuntimeException("Error while uploading file.");
			System.out.println("Amazon"+exception.getMessage());
		}
		return null;
	}

}
