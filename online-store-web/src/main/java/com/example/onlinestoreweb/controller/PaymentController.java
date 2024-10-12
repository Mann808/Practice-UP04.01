package com.example.onlinestoreweb.controller;

import com.example.onlinestoreweb.dto.Payment;
import com.example.onlinestoreweb.service.OrderService;
import com.example.onlinestoreweb.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    public PaymentController(PaymentService paymentService, OrderService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    @GetMapping
    public String listPayments(Model model) {
        model.addAttribute("payments", paymentService.getAllPayments());
        return "payments/list";
    }

    @GetMapping("/{id}")
    public String viewPayment(@PathVariable Long id, Model model) {
        model.addAttribute("payment", paymentService.getPaymentById(id));
        return "payments/view";
    }

    @GetMapping("/new")
    public String newPaymentForm(Model model) {
        model.addAttribute("payment", new Payment());
        model.addAttribute("orders", orderService.getAllOrders());
        return "payments/create";
    }

    @PostMapping
    public String createPayment(@Valid @ModelAttribute Payment payment, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("orders", orderService.getAllOrders());
            return "payments/create";
        }
        paymentService.createPayment(payment);
        return "redirect:/payments";
    }

    @GetMapping("/edit/{id}")
    public String editPaymentForm(@PathVariable Long id, Model model) {
        model.addAttribute("payment", paymentService.getPaymentById(id));
        model.addAttribute("orders", orderService.getAllOrders());
        return "payments/edit";
    }

    @PostMapping("/update/{id}")
    public String updatePayment(@PathVariable Long id, @Valid @ModelAttribute Payment payment, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("orders", orderService.getAllOrders());
            return "payments/edit";
        }
        paymentService.updatePayment(id, payment);
        return "redirect:/payments";
    }

    @GetMapping("/delete/{id}")
    public String deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return "redirect:/payments";
    }
}