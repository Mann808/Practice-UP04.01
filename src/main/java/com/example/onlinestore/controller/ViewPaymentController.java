package com.example.onlinestore.controller;

import com.example.onlinestore.entity.Payment;
import com.example.onlinestore.entity.Order;
import com.example.onlinestore.service.PaymentService;
import com.example.onlinestore.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payments")
public class ViewPaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    @Autowired
    public ViewPaymentController(PaymentService paymentService, OrderService orderService){
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    // Добавлено
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping
    public String listPayments(Model model){
        List<Payment> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "payments/list";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/new")
    public String showCreateForm(Model model){
        model.addAttribute("payment", new Payment());
        model.addAttribute("orders", orderService.getAllOrders());
        return "payments/create";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping
    public String createPayment(@Valid @ModelAttribute("payment") Payment payment, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("orders", orderService.getAllOrders());
            return "payments/create";
        }
        paymentService.createPayment(payment);
        return "redirect:/payments";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        Payment payment = paymentService.getPaymentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid payment Id:" + id));
        model.addAttribute("payment", payment);
        model.addAttribute("orders", orderService.getAllOrders());
        return "payments/edit";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/update/{id}")
    public String updatePayment(@PathVariable("id") Long id, @Valid @ModelAttribute("payment") Payment payment, BindingResult result, Model model){
        if(result.hasErrors()){
            payment.setId(id);
            model.addAttribute("orders", orderService.getAllOrders());
            return "payments/edit";
        }
        paymentService.updatePayment(id, payment);
        return "redirect:/payments";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deletePayment(@PathVariable("id") Long id, Model model){
        paymentService.deletePayment(id);
        return "redirect:/payments";
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("/{id}")
    public String viewPayment(@PathVariable("id") Long id, Model model){
        Payment payment = paymentService.getPaymentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid payment Id:" + id));
        model.addAttribute("payment", payment);
        return "payments/view";
    }
}
