package com.cinema.service.impl;

import com.cinema.dao.OrderDao;
import com.cinema.lib.Inject;
import com.cinema.lib.Service;
import com.cinema.model.Order;
import com.cinema.model.Ticket;
import com.cinema.model.User;
import com.cinema.service.OrderService;
import com.cinema.service.ShoppingCartService;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderDao orderDao;
    @Inject
    private ShoppingCartService cartService;

    @Override
    public Order completeOrder(List<Ticket> tickets, User user) {
        Order order = new Order();
        order.setTickets(tickets);
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
