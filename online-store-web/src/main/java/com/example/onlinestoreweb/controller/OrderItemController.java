package com.example.onlinestoreweb.controller;

import com.example.onlinestoreweb.dto.OrderItem;
import com.example.onlinestoreweb.service.OrderItemService;
import com.example.onlinestoreweb.service.OrderService;
import com.example.onlinestoreweb.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final OrderService orderService;
    private final ProductService productService;

    public OrderItemController(OrderItemService orderItemService, OrderService orderService, ProductService productService) {
        this.orderItemService = orderItemService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping
    public String listOrderItems(Model model) {
        model.addAttribute("orderItems", orderItemService.getAllOrderItems());
        return "orderitems/list";
    }

    @GetMapping("/{id}")
    public String viewOrderItem(@PathVariable Long id, Model model) {
        model.addAttribute("orderItem", orderItemService.getOrderItemById(id));
        return "orderitems/view";
    }

    @GetMapping("/new")
    public String newOrderItemForm(Model model) {
        model.addAttribute("orderItem", new OrderItem());
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("products", productService.getAllProducts());
        return "orderitems/create";
    }

    @PostMapping
    public String createOrderItem(@Valid @ModelAttribute OrderItem orderItem, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("products", productService.getAllProducts());
            return "orderitems/create";
        }
        orderItemService.createOrderItem(orderItem);
        return "redirect:/order-items";
    }

    @GetMapping("/edit/{id}")
    public String editOrderItemForm(@PathVariable Long id, Model model) {
        model.addAttribute("orderItem", orderItemService.getOrderItemById(id));
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("products", productService.getAllProducts());
        return "orderitems/edit";
    }

    @PostMapping("/update/{id}")
    public String updateOrderItem(@PathVariable Long id, @Valid @ModelAttribute OrderItem orderItem, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("products", productService.getAllProducts());
            return "orderitems/edit";
        }
        orderItemService.updateOrderItem(id, orderItem);
        return "redirect:/order-items";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return "redirect:/order-items";
    }
}