package com.example.onlinestoreweb.controller;

import com.example.onlinestoreweb.dto.Order;
import com.example.onlinestoreweb.service.OrderService;
import com.example.onlinestoreweb.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders/list";
    }

    @GetMapping("/{id}")
    public String viewOrder(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.getOrderById(id));
        return "orders/view";
    }

    @GetMapping("/new")
    public String newOrderForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("users", userService.getAllUsers());
        return "orders/create";
    }

    @PostMapping
    public String createOrder(@Valid @ModelAttribute Order order, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("users", userService.getAllUsers());
            return "orders/create";
        }
        orderService.createOrder(order);
        return "redirect:/orders";
    }

    @GetMapping("/edit/{id}")
    public String editOrderForm(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        model.addAttribute("users", userService.getAllUsers());
        return "orders/edit";
    }

    @PostMapping("/update/{id}")
    public String updateOrder(@PathVariable Long id, @Valid @ModelAttribute Order order, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("users", userService.getAllUsers());
            return "orders/edit";
        }
        orderService.updateOrder(id, order);
        return "redirect:/orders";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}