package com.example.onlinestoreweb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Category {
    private Long id;

    @NotBlank(message = "Название категории не может быть пустым")
    @Size(max = 50, message = "Название категории не может быть длиннее 50 символов")
    private String name;
}