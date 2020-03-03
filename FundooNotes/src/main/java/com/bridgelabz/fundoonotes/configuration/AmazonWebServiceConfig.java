package com.bridgelabz.fundoonotes.configuration;


	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;

	import com.amazonaws.auth.AWSStaticCredentialsProvider;
	import com.amazonaws.auth.BasicAWSCredentials;
	import com.amazonaws.regions.Regions;
	import com.amazonaws.services.s3.AmazonS3;
	import com.amazonaws.services.s3.AmazonS3ClientBuilder;

	@Configuration
	public class AmazonWebServiceConfig {
	

	    @Value("${key}")
	    private String awsKeyId;

	    @Value("${accesskey}")
	    private String accessKey;

	    @Value("${region}")
	    private String region;

	    @Bean
	    public AmazonS3 awsS3Client() {
	        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey,awsKeyId);
	        return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region))
	                .withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
	    }
	}
	
	
	
	
	
	


