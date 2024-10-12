package com.example.onlinestoreweb.service;

import com.example.onlinestoreweb.dto.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductService {

    private final WebClient webClient;
    private final String apiBaseUrl;

    public ProductService(WebClient webClient, @Value("${api.base.url}") String apiBaseUrl) {
        this.webClient = webClient;
        this.apiBaseUrl = apiBaseUrl;
    }

    public List<Product> getAllProducts() {
        return webClient.get()
                .uri(apiBaseUrl + "/products")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Product>>() {})
                .block();
    }

    public Product getProductById(Long id) {
        return webClient.get()
                .uri(apiBaseUrl + "/products/" + id)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }

    public Product createProduct(Product product) {
        return webClient.post()
                .uri(apiBaseUrl + "/products")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(product), Product.class)
                .retrieve()
                .bodyToMono(Product.class)
                .block();
    }

    public void updateProduct(Long id, Product product) {
        webClient.put()
                .uri(apiBaseUrl + "/products/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void deleteProduct(Long id) {
        webClient.delete()
                .uri(apiBaseUrl + "/products/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}