package com.example.onlinestore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Улица обязательна для заполнения")
    @Size(max = 100, message = "Улица может содержать до 100 символов")
    private String street;

    @NotBlank(message = "Город обязателен для заполнения")
    @Size(max = 50, message = "Город может содержать до 50 символов")
    private String city;

    @NotBlank(message = "Штат обязателен для заполнения")
    @Size(max = 50, message = "Штат может содержать до 50 символов")
    private String state;

    @NotBlank(message = "Почтовый код обязателен для заполнения")
    @Size(max = 20, message = "Почтовый код может содержать до 20 символов")
    private String postalCode;

    @NotBlank(message = "Страна обязательна для заполнения")
    @Size(max = 50, message = "Страна может содержать до 50 символов")
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    @NotNull(message = "Пользователь обязателен для адреса")
    private User user;
    // Constructors
    public Address() {}

    public Address(String street, String city, String state, String postalCode, String country, User user) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.user = user;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Добавляем сеттер для id
    public void setId(Long id) {
        this.id = id;
    }
}
