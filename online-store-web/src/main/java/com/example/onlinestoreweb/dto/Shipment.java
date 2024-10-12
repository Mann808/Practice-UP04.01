package com.example.onlinestoreweb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Shipment {
    private Long id;

    @NotBlank(message = "Метод доставки не может быть пустым")
    @Size(max = 50, message = "Метод доставки не может быть длиннее 50 символов")
    private String shipmentMethod;

    @NotNull(message = "Дата доставки не может быть пустой")
    private LocalDateTime shipmentDate;

    @NotNull(message = "Заказ не может быть пустым")
    private Order order;
}