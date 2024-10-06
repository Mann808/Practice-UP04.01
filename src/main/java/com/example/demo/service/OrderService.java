package com.example.demo.service;

import com.example.demo.entity.Order;
import com.example.demo.entity.User;
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
