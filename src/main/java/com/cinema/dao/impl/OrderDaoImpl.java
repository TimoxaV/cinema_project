package com.cinema.dao.impl;

import com.cinema.dao.OrderDao;
import com.cinema.exceptions.DataProcessingException;
import com.cinema.model.Order;
import com.cinema.model.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl implements OrderDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public OrderDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Order add(Order order) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(order);
            transaction.commit();
            return order;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't insert Order entity " + order, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Order> getUsersOrders(User user) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select distinct o from Order o join fetch o.tickets "
                    + "join fetch o.user where o.user.id = :userId ", Order.class)
                    .setParameter("userId", user.getId())
                    .getResultList();
        }
    }
}
