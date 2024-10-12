package com.example.onlinestoreweb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class Order {
    private Long id;

    @NotNull(message = "Дата заказа не может быть пустой")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime orderDate;

    @NotNull(message = "Пользователь не может быть пустым")
    private User user;

    private Set<OrderItem> orderItems;
}