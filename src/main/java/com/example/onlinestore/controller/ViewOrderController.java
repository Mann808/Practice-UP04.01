package com.example.onlinestore.controller;

import com.example.onlinestore.entity.Order;
import com.example.onlinestore.entity.User;
import com.example.onlinestore.service.OrderService;
import com.example.onlinestore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class ViewOrderController {

    private final OrderService orderService;
    private final UserService userService;

    @Autowired
    public ViewOrderController(OrderService orderService, UserService userService){
        this.orderService = orderService;
        this.userService = userService;
    }

    // Добавлено
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping
    public String listOrders(Model model){
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders/list";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/new")
    public String showCreateForm(Model model){
        model.addAttribute("order", new Order());
        model.addAttribute("users", userService.getAllUsers());
        return "orders/create";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping
    public String createOrder(@Valid @ModelAttribute("order") Order order, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("users", userService.getAllUsers());
            return "orders/create";
        }
        orderService.createOrder(order);
        return "redirect:/orders";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + id));
        model.addAttribute("order", order);
        model.addAttribute("users", userService.getAllUsers());
        return "orders/edit";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/update/{id}")
    public String updateOrder(@PathVariable("id") Long id, @Valid @ModelAttribute("order") Order order, BindingResult result, Model model){
        if(result.hasErrors()){
            order.setId(id);
            model.addAttribute("users", userService.getAllUsers());
            return "orders/edit";
        }
        orderService.updateOrder(id, order);
        return "redirect:/orders";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable("id") Long id, Model model){
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("/{id}")
    public String viewOrder(@PathVariable("id") Long id, Model model){
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid order Id:" + id));
        model.addAttribute("order", order);
        return "orders/view";
    }

}
