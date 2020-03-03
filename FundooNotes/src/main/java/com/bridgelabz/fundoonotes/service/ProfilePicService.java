package com.bridgelabz.fundoonotes.service;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.entity.Profile;

public interface ProfilePicService {

	Profile storeObjectInS3(MultipartFile file, String originalFilename, String contentType, String token);

}
