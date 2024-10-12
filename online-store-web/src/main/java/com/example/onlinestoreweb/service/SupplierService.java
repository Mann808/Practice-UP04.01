package com.example.onlinestoreweb.service;

import com.example.onlinestoreweb.dto.Supplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class SupplierService {

    private final WebClient webClient;
    private final String apiBaseUrl;

    public SupplierService(WebClient webClient, @Value("${api.base.url}") String apiBaseUrl) {
        this.webClient = webClient;
        this.apiBaseUrl = apiBaseUrl;
    }

    public List<Supplier> getAllSuppliers() {
        return webClient.get()
                .uri(apiBaseUrl + "/suppliers")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Supplier>>() {})
                .block();
    }

    public Supplier getSupplierById(Long id) {
        return webClient.get()
                .uri(apiBaseUrl + "/suppliers/" + id)
                .retrieve()
                .bodyToMono(Supplier.class)
                .block();
    }

    public Supplier createSupplier(Supplier supplier) {
        return webClient.post()
                .uri(apiBaseUrl + "/suppliers")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(supplier), Supplier.class)
                .retrieve()
                .bodyToMono(Supplier.class)
                .block();
    }

    public void updateSupplier(Long id, Supplier supplier) {
        webClient.put()
                .uri(apiBaseUrl + "/suppliers/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(supplier), Supplier.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void deleteSupplier(Long id) {
        webClient.delete()
                .uri(apiBaseUrl + "/suppliers/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}