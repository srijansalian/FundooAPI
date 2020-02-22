package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.LabelInformation;

/**
 * 
 * @author Srijan Kumar
 *
 */

@Repository
public interface LabelRepo extends JpaRepository<LabelInformation, Long> {

	@Query("from LabelInformation where user_id=:id and name=:name")
	LabelInformation fetchlabel(long id, String name);

	@Query("from LabelInformation where label_id=:id ")
	LabelInformation fetchbyId(long id);

	@Modifying
	@Query("DELETE FROM LabelInformation WHERE label_id=:id")
	void deletebyId(long id);

	@Query("from LabelInformation where user_id=:id ")
	List<LabelInformation> fetchlabelbyId(long id);

}
