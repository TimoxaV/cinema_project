package com.cinema.dao.impl;

import com.cinema.dao.MovieSessionDao;
import com.cinema.exceptions.DataProcessingException;
import com.cinema.model.MovieSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MovieSessionDaoImpl implements MovieSessionDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public MovieSessionDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = sessionFactory.openSession()) {
            Query<MovieSession> query = session.createQuery("FROM MovieSession WHERE movie.id "
                    + "= :movieId AND showTime BETWEEN :dateFrom AND :dateTo",
                    MovieSession.class);
            query.setParameter("movieId", movieId);
            query.setParameter("dateFrom", date.atStartOfDay());
            query.setParameter("dateTo", date.atTime(LocalTime.MAX));
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find sessions for movie with id "
                    + movieId, e);
        }
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(movieSession);
            transaction.commit();
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert MovieSession entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
