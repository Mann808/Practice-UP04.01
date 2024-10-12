package com.example.onlinestoreweb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Supplier {
    private Long id;

    @NotBlank(message = "Название поставщика не может быть пустым")
    @Size(max = 100, message = "Название поставщика не может быть длиннее 100 символов")
    private String name;

    @NotBlank(message = "Email контакта не может быть пустым")
    @Email(message = "Некорректный формат email")
    private String contactEmail;
}