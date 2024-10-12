package com.example.onlinestoreweb.service;

import com.example.onlinestoreweb.dto.OrderItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OrderItemService {

    private final WebClient webClient;
    private final String apiBaseUrl;

    public OrderItemService(WebClient webClient, @Value("${api.base.url}") String apiBaseUrl) {
        this.webClient = webClient;
        this.apiBaseUrl = apiBaseUrl;
    }

    public List<OrderItem> getAllOrderItems() {
        return webClient.get()
                .uri(apiBaseUrl + "/order-items")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<OrderItem>>() {})
                .block();
    }

    public OrderItem getOrderItemById(Long id) {
        return webClient.get()
                .uri(apiBaseUrl + "/order-items/" + id)
                .retrieve()
                .bodyToMono(OrderItem.class)
                .block();
    }

    public OrderItem createOrderItem(OrderItem orderItem) {
        return webClient.post()
                .uri(apiBaseUrl + "/order-items")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(orderItem), OrderItem.class)
                .retrieve()
                .bodyToMono(OrderItem.class)
                .block();
    }

    public void updateOrderItem(Long id, OrderItem orderItem) {
        webClient.put()
                .uri(apiBaseUrl + "/order-items/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(orderItem), OrderItem.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void deleteOrderItem(Long id) {
        webClient.delete()
                .uri(apiBaseUrl + "/order-items/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}