package com.cinema.service.impl;

import com.cinema.dao.OrderDao;
import com.cinema.model.Order;
import com.cinema.model.Ticket;
import com.cinema.model.User;
import com.cinema.service.OrderService;
import com.cinema.service.ShoppingCartService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final ShoppingCartService cartService;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, ShoppingCartService cartService) {
        this.orderDao = orderDao;
        this.cartService = cartService;
    }

    @Override
    public Order completeOrder(List<Ticket> tickets, User user) {
        Order order = new Order();
        order.setTickets(new ArrayList<>(tickets));
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order = orderDao.add(order);
        cartService.clear(cartService.getByUser(user));
        return order;
    }

    @Override
    public List<Order> getOrderHistory(User user) {
        return orderDao.getUsersOrders(user);
    }
}
