package com.example.onlinestoreapi.repository;

import com.example.onlinestoreapi.entity.Order;
import com.example.onlinestoreapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findByUser(User user);
}
