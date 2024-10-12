package com.example.onlinestoreweb.service;

import com.example.onlinestoreweb.dto.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class UserService {

    private final WebClient webClient;
    private final String apiBaseUrl;

    public UserService(WebClient webClient, @Value("${api.base.url}") String apiBaseUrl) {
        this.webClient = webClient;
        this.apiBaseUrl = apiBaseUrl;
    }

    public List<User> getAllUsers() {
        String response = webClient.get()
                .uri(apiBaseUrl + "/users")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println("API Response: " + response);
        return webClient.get()
                .uri(apiBaseUrl + "/users")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<User>>() {})
                .block();
    }

    public User getUserById(Long id) {
        return webClient.get()
                .uri(apiBaseUrl + "/users/" + id)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }

    public User createUser(User user) {
        return webClient.post()
                .uri(apiBaseUrl + "/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(user), User.class)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }

    public void updateUser(Long id, User user) {
        webClient.put()
                .uri(apiBaseUrl + "/users/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(user), User.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void deleteUser(Long id) {
        webClient.delete()
                .uri(apiBaseUrl + "/users/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}