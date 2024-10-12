package com.example.onlinestore.service;

import com.example.onlinestore.entity.Order;
import com.example.onlinestore.entity.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> getAllOrders();
    List<Order> getOrdersByUser(User user);
    Optional<Order> getOrderById(Long id);
    Order updateOrder(Long id, Order order);
    void deleteOrder(Long id);
}
