package com.example.onlinestoreweb.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItem {
    private Long id;

    @NotNull(message = "Количество не может быть пустым")
    @Min(value = 1, message = "Количество должно быть не менее 1")
    private Integer quantity;

    @NotNull(message = "Цена за единицу не может быть пустой")
    @Min(value = 0, message = "Цена за единицу не может быть отрицательной")
    private BigDecimal unitPrice;

    @NotNull(message = "Заказ не может быть пустым")
    private Order order;

    @NotNull(message = "Продукт не может быть пустым")
    private Product product;
}