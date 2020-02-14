package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.dto.PasswordUpdate;
import com.bridgelabz.fundoonotes.entity.UserInformation;

@Repository
public class UserRepositoryImplementation implements UserRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public UserInformation save(UserInformation userInfromation) {

		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(userInfromation);
		return userInfromation;
	}

	@Override
	public UserInformation getUser(String email) {

		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("FROM UserInformation where email =:email");
		q.setParameter("email", email);
		return (UserInformation) q.uniqueResult();
	}

	@Override
	public UserInformation getUserById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("FROM UserInformation where id=:id");
		q.setParameter("id", id);
		return (UserInformation) q.uniqueResult();

	}

	@Override
	public boolean upDate(PasswordUpdate information, Long id) {

		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("update UserInformation set password =:p" + " " + " " + "where id=:i");
		q.setParameter("p", information.getConfirmpassword());
		q.setParameter("i", id);
		int status = q.executeUpdate();
		if (status > 0) {
			return true;

		} else {
			return false;
		}
	}

	@Override
	public boolean verify(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("update UserInformation set is_verified =:p" + " " + " " + " where id=:i");
		q.setParameter("p", true);
		q.setParameter("i", id);
		int status = q.executeUpdate();
		if (status > 0) {
			return true;

		} else {
			return false;
		}

	}

	@Override
	public List<UserInformation> getUsers() {
		Session currentSession = entityManager.unwrap(Session.class);
		List usersList = currentSession.createQuery("from UserInformation").getResultList();
		return usersList;

	}

}
