package com.bridgelabz.fundoonotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.Profile;
@Repository
public interface ProfilePicture extends JpaRepository<Profile ,Long> {
	@Query(value =  "select * from profile where user_id = ?" , nativeQuery =  true)
	Profile findByUserId(Long userId);
	
	@Modifying
	@Query("delete from Profile where user_id =:userId")
	void deletebyId(Long userId);
	
	@Modifying
	@Query("update from Profile set profile_name=:originalFilename  where user_id=:id")
	void updateprofile(Long id,String originalFilename );
	

}
