package com.example.onlinestoreapi.service;

import com.example.onlinestoreapi.entity.Order;
import com.example.onlinestoreapi.entity.User;
import com.example.onlinestoreapi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        return List.of();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order updateOrder(Long id, Order orderDetails) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));

        order.setOrderDate(orderDetails.getOrderDate());
        order.setUser(orderDetails.getUser());
        order.setOrderItems(orderDetails.getOrderItems());
        order.setShipment(orderDetails.getShipment());
        order.setPayment(orderDetails.getPayment());

        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
        orderRepository.delete(order);
    }
}