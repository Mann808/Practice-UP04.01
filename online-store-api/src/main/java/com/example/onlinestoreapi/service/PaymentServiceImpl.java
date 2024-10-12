package com.example.onlinestoreapi.service;

import com.example.onlinestoreapi.entity.Payment;
import com.example.onlinestoreapi.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public Payment updatePayment(Long id, Payment paymentDetails) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id " + id));

        payment.setPaymentMethod(paymentDetails.getPaymentMethod());
        payment.setPaymentDate(paymentDetails.getPaymentDate());
        payment.setAmount(paymentDetails.getAmount());
        payment.setOrder(paymentDetails.getOrder());

        return paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id " + id));
        paymentRepository.delete(payment);
    }
}