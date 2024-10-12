package com.example.onlinestoreweb.service;

import com.example.onlinestoreweb.dto.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OrderService {

    private final WebClient webClient;
    private final String apiBaseUrl;

    public OrderService(WebClient webClient, @Value("${api.base.url}") String apiBaseUrl) {
        this.webClient = webClient;
        this.apiBaseUrl = apiBaseUrl;
    }

    public List<Order> getAllOrders() {
        return webClient.get()
                .uri(apiBaseUrl + "/orders")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Order>>() {})
                .block();
    }

    public Order getOrderById(Long id) {
        return webClient.get()
                .uri(apiBaseUrl + "/orders/" + id)
                .retrieve()
                .bodyToMono(Order.class)
                .block();
    }

    public Order createOrder(Order order) {
        return webClient.post()
                .uri(apiBaseUrl + "/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(order), Order.class)
                .retrieve()
                .bodyToMono(Order.class)
                .block();
    }

    public void updateOrder(Long id, Order order) {
        webClient.put()
                .uri(apiBaseUrl + "/orders/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(order), Order.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void deleteOrder(Long id) {
        webClient.delete()
                .uri(apiBaseUrl + "/orders/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}