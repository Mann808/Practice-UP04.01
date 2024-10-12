package com.example.onlinestoreweb.service;

import com.example.onlinestoreweb.dto.Shipment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ShipmentService {

    private final WebClient webClient;
    private final String apiBaseUrl;

    public ShipmentService(WebClient webClient, @Value("${api.base.url}") String apiBaseUrl) {
        this.webClient = webClient;
        this.apiBaseUrl = apiBaseUrl;
    }

    public List<Shipment> getAllShipments() {
        return webClient.get()
                .uri(apiBaseUrl + "/shipments")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Shipment>>() {})
                .block();
    }

    public Shipment getShipmentById(Long id) {
        return webClient.get()
                .uri(apiBaseUrl + "/shipments/" + id)
                .retrieve()
                .bodyToMono(Shipment.class)
                .block();
    }

    public Shipment createShipment(Shipment shipment) {
        return webClient.post()
                .uri(apiBaseUrl + "/shipments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(shipment), Shipment.class)
                .retrieve()
                .bodyToMono(Shipment.class)
                .block();
    }

    public void updateShipment(Long id, Shipment shipment) {
        webClient.put()
                .uri(apiBaseUrl + "/shipments/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(shipment), Shipment.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void deleteShipment(Long id) {
        webClient.delete()
                .uri(apiBaseUrl + "/shipments/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}