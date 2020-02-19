package com.bridgelabz.fundoonotes.repository;

import java.util.List;

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
	public NoteInformation save(NoteInformation noteInformation) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(noteInformation);

		return noteInformation;
	}

	@Override
	public NoteInformation findbyId(Long id) {
System.out.println(id);
		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("FROM NoteInformation where id=:id");
		q.setParameter("id", id);
		return (NoteInformation) q.uniqueResult();
	}

	@Override
	public boolean deleteNode(Long id, long userid) {
		Session session = entityManager.unwrap(Session.class);
		// System.out.println(id);

		// String qry = "DELETE FROM NoteInformation WHERE id=:id";
		Query q = session.createQuery("DELETE FROM NoteInformation " + "WHERE id = :id ");
		// Query q = session.createQuery(qry););
		q.setParameter("id", id);

		int result = q.executeUpdate();
		// System.out.println("hello");
		System.out.println(result);
		if (result >= 1) {
			return true;
		} else {
			return false;

		}

	}
	@SuppressWarnings("unchecked")
	@Override
public List<NoteInformation> getNotes(long userid) {
		
		System.out.println("in repository");
		Session session = entityManager.unwrap(Session.class);
		return session.createQuery(
				"from NoteInformation where user_id='" + userid + "'" + " and is_trashed=false and is_archieved=false ORDER BY id DESC")
				.getResultList();
	}

}
