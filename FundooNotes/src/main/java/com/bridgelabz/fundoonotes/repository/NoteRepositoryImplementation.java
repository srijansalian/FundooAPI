package com.bridgelabz.fundoonotes.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.dto.NoteUpdate;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
/**
 * 
 * @author Srijan Kumar
 *
 */
@Repository
public class NoteRepositoryImplementation implements NoteRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public NoteInformation save(NoteInformation noteInformation){
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(noteInformation);

		return noteInformation;
	}

	@Override
	public NoteInformation findbyId(Long id) {

		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("FROM NoteInformation where id=:id");
		q.setParameter("id", id);
		return (NoteInformation) q.uniqueResult();
	}

	@Override
	public boolean deleteNode(Long id) {
		
		return false;
	}

}
