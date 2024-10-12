package com.example.onlinestoreweb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Address {
    private Long id;

    @NotBlank(message = "Улица не может быть пустой")
    @Size(max = 100, message = "Улица не может быть длиннее 100 символов")
    private String street;

    @NotBlank(message = "Город не может быть пустым")
    @Size(max = 50, message = "Город не может быть длиннее 50 символов")
    private String city;

    @NotBlank(message = "Штат не может быть пустым")
    @Size(max = 50, message = "Штат не может быть длиннее 50 символов")
    private String state;

    @NotBlank(message = "Почтовый индекс не может быть пустым")
    @Size(max = 10, message = "Почтовый индекс не может быть длиннее 10 символов")
    private String postalCode;

    @NotBlank(message = "Страна не может быть пустой")
    @Size(max = 50, message = "Страна не может быть длиннее 50 символов")
    private String country;

    private User user;
}