package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Participant;

@Component("participantService")
public class ParticipantService {

	DatabaseConnector connector;

	public ParticipantService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Participant> getAll() {
		String hql = "FROM Participant";
		Query query = connector.getSession().createQuery(hql);
		return query.list();
	}

	public Participant findByLogin(String login) {
		String hql = "FROM Participant WHERE login = :login";
		Query query = connector.getSession().createQuery(hql);
		query.setParameter("login", login);
		return (Participant) query.uniqueResult();
	}

	public Participant addParticipant(Participant participant) {
		Transaction tx = connector.getSession().beginTransaction();
		connector.getSession().save(participant);
		tx.commit();
		return participant;
	}

	public Participant removeParticipant(Participant participant) {
		Transaction tx = connector.getSession().beginTransaction();
		connector.getSession().delete(participant);
		tx.commit();
		return participant;
	}

	public Participant updateParticipant(Participant participant, String login) {
		Transaction tx = connector.getSession().beginTransaction();
		Participant participant1 = connector.getSession().get(Participant.class, login);
		participant1.setPassword(participant.getPassword());
		connector.getSession().save(participant1);
		tx.commit();
		return participant;
	}


}
