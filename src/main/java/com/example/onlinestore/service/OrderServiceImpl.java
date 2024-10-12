package com.example.onlinestore.service;

import com.example.onlinestore.entity.Order;
import com.example.onlinestore.entity.User;
import com.example.onlinestore.exception.ResourceNotFoundException;
import com.example.onlinestore.repository.OrderRepository;
import com.example.onlinestore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository){
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Order createOrder(Order order){
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByUser(User user){
        return orderRepository.findByUser(user);
    }

    @Override
    public Optional<Order> getOrderById(Long id){
        return orderRepository.findById(id);
    }

    @Override
    public Order updateOrder(Long id, Order orderDetails){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
        order.setOrderDate(orderDetails.getOrderDate());
        order.setUser(orderDetails.getUser());
        // Update other fields as necessary
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
        orderRepository.delete(order);
    }
}
