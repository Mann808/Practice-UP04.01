package com.example.onlinestoreweb.service;

import com.example.onlinestoreweb.dto.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PaymentService {

    private final WebClient webClient;
    private final String apiBaseUrl;

    public PaymentService(WebClient webClient, @Value("${api.base.url}") String apiBaseUrl) {
        this.webClient = webClient;
        this.apiBaseUrl = apiBaseUrl;
    }

    public List<Payment> getAllPayments() {
        return webClient.get()
                .uri(apiBaseUrl + "/payments")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Payment>>() {})
                .block();
    }

    public Payment getPaymentById(Long id) {
        return webClient.get()
                .uri(apiBaseUrl + "/payments/" + id)
                .retrieve()
                .bodyToMono(Payment.class)
                .block();
    }

    public Payment createPayment(Payment payment) {
        return webClient.post()
                .uri(apiBaseUrl + "/payments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(payment), Payment.class)
                .retrieve()
                .bodyToMono(Payment.class)
                .block();
    }

    public void updatePayment(Long id, Payment payment) {
        webClient.put()
                .uri(apiBaseUrl + "/payments/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(payment), Payment.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void deletePayment(Long id) {
        webClient.delete()
                .uri(apiBaseUrl + "/payments/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}