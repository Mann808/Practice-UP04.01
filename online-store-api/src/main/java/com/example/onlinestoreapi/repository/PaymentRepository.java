package com.example.onlinestoreapi.repository;

import com.example.onlinestoreapi.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{
}
