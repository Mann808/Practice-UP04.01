package com.example.onlinestoreweb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Payment {
    private Long id;


    @NotBlank(message = "Метод оплаты не может быть пустым")
    @Size(max = 50, message = "Метод оплаты не может быть длиннее 50 символов")
    private String paymentMethod;

    @NotNull(message = "Дата платежа не может быть пустой")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime paymentDate;

    @NotNull(message = "Сумма не может быть пустой")
    @Min(value = 0, message = "Сумма не может быть отрицательной")
    private BigDecimal amount;

    @NotNull(message = "Заказ не может быть пустым")
    private Order order;
}