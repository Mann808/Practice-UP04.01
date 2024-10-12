package com.example.onlinestoreapi.service;

import com.example.onlinestoreapi.entity.OrderItem;
import com.example.onlinestoreapi.repository.OrderItemRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        orderItems.forEach(item -> {
            Hibernate.initialize(item.getOrder());
            Hibernate.initialize(item.getProduct());
        });
        return orderItems;
    }

    @Override
    public Optional<OrderItem> getOrderItemById(Long id) {
        return orderItemRepository.findById(id);
    }

    @Override
    public OrderItem updateOrderItem(Long id, OrderItem orderItemDetails) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with id " + id));

        orderItem.setQuantity(orderItemDetails.getQuantity());
        orderItem.setUnitPrice(orderItemDetails.getUnitPrice());
        orderItem.setOrder(orderItemDetails.getOrder());
        orderItem.setProduct(orderItemDetails.getProduct());

        return orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteOrderItem(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with id " + id));
        orderItemRepository.delete(orderItem);
    }
}