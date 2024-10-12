package com.example.onlinestoreweb.service;

import com.example.onlinestoreweb.dto.Address;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AddressService {

    private final WebClient webClient;
    private final String apiBaseUrl;

    public AddressService(WebClient webClient, @Value("${api.base.url}") String apiBaseUrl) {
        this.webClient = webClient;
        this.apiBaseUrl = apiBaseUrl;
    }

    public List<Address> getAllAddresses() {
        return webClient.get()
                .uri(apiBaseUrl + "/addresses")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Address>>() {})
                .block();
    }

    public Address getAddressById(Long id) {
        return webClient.get()
                .uri(apiBaseUrl + "/addresses/" + id)
                .retrieve()
                .bodyToMono(Address.class)
                .block();
    }

    public Address createAddress(Address address) {
        return webClient.post()
                .uri(apiBaseUrl + "/addresses")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(address), Address.class)
                .retrieve()
                .bodyToMono(Address.class)
                .block();
    }

    public void updateAddress(Long id, Address address) {
        webClient.put()
                .uri(apiBaseUrl + "/addresses/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(address), Address.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void deleteAddress(Long id) {
        webClient.delete()
                .uri(apiBaseUrl + "/addresses/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}