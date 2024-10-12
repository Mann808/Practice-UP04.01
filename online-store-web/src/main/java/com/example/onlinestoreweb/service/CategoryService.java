package com.example.onlinestoreweb.service;

import com.example.onlinestoreweb.dto.Category;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CategoryService {

    private final WebClient webClient;
    private final String apiBaseUrl;

    public CategoryService(WebClient webClient, @Value("${api.base.url}") String apiBaseUrl) {
        this.webClient = webClient;
        this.apiBaseUrl = apiBaseUrl;
    }

    public List<Category> getAllCategories() {
        return webClient.get()
                .uri(apiBaseUrl + "/categories")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Category>>() {})
                .block();
    }

    public Category getCategoryById(Long id) {
        return webClient.get()
                .uri(apiBaseUrl + "/categories/" + id)
                .retrieve()
                .bodyToMono(Category.class)
                .block();
    }

    public Category createCategory(Category category) {
        return webClient.post()
                .uri(apiBaseUrl + "/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(category), Category.class)
                .retrieve()
                .bodyToMono(Category.class)
                .block();
    }

    public void updateCategory(Long id, Category category) {
        webClient.put()
                .uri(apiBaseUrl + "/categories/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(category), Category.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void deleteCategory(Long id) {
        webClient.delete()
                .uri(apiBaseUrl + "/categories/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}