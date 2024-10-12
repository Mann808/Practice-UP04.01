package com.example.onlinestoreapi.service;

import com.example.onlinestoreapi.entity.Payment;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    Payment createPayment(Payment payment);
    List<Payment> getAllPayments();
    Optional<Payment> getPaymentById(Long id);
    Payment updatePayment(Long id, Payment payment);
    void deletePayment(Long id);
}
