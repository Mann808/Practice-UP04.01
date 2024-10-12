package com.example.onlinestoreweb.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class Product {
    private Long id;

    @NotBlank(message = "Название продукта не может быть пустым")
    @Size(max = 100, message = "Название продукта не может быть длиннее 100 символов")
    private String name;

    @NotBlank(message = "Описание продукта не может быть пустым")
    @Size(max = 500, message = "Описание продукта не может быть длиннее 500 символов")
    private String description;

    @NotNull(message = "Цена не может быть пустой")
    @Min(value = 0, message = "Цена не может быть отрицательной")
    private BigDecimal price;

    @NotNull(message = "Категория не может быть пустой")
    private Category category;

    @NotNull(message = "Поставщик не может быть пустым")
    private Supplier supplier;
}