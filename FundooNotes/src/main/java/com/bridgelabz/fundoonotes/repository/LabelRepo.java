package com.bridgelabz.fundoonotes.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.LabelInformation;

@Repository
public interface LabelRepo extends JpaRepository<LabelInformation, Long> {

	@Query("from LabelInformation where user_id=:id and name=:name")
	LabelInformation fetchlabel(long id, String name);

	@Query("from LabelInformation where label_id=:id ")
	LabelInformation fetchbyId(long id);

	@Transactional
	@Modifying
	@Query("DELETE FROM LabelInformation WHERE label_id=:id")
	void deletebyId(long id);
	
}
