package com.example.onlinestore.controller;

import com.example.onlinestore.entity.OrderItem;
import com.example.onlinestore.entity.Order;
import com.example.onlinestore.entity.Product;
import com.example.onlinestore.service.OrderItemService;
import com.example.onlinestore.service.OrderService;
import com.example.onlinestore.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order-items")
public class ViewOrderItemController {

    private final OrderItemService orderItemService;
    private final OrderService orderService;
    private final ProductService productService;

    @Autowired
    public ViewOrderItemController(OrderItemService orderItemService, OrderService orderService, ProductService productService){
        this.orderItemService = orderItemService;
        this.orderService = orderService;
        this.productService = productService;
    }

    // Добавлено
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping
    public String listOrderItems(Model model){
        List<OrderItem> orderItems = orderItemService.getAllOrderItems();
        model.addAttribute("orderItems", orderItems);
        return "orderitems/list";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/new")
    public String showCreateForm(Model model){
        model.addAttribute("orderItem", new OrderItem());
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("products", productService.getAllProducts());
        return "orderitems/create";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping
    public String createOrderItem(@Valid @ModelAttribute("orderItem") OrderItem orderItem, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("products", productService.getAllProducts());
            return "orderitems/create";
        }
        orderItemService.createOrderItem(orderItem);
        return "redirect:/order-items";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        OrderItem orderItem = orderItemService.getOrderItemById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid orderItem Id:" + id));
        model.addAttribute("orderItem", orderItem);
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("products", productService.getAllProducts());
        return "orderitems/edit";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/update/{id}")
    public String updateOrderItem(@PathVariable("id") Long id, @Valid @ModelAttribute("orderItem") OrderItem orderItem, BindingResult result, Model model){
        if(result.hasErrors()){
            orderItem.setId(id);
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("products", productService.getAllProducts());
            return "orderitems/edit";
        }
        orderItemService.updateOrderItem(id, orderItem);
        return "redirect:/order-items";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteOrderItem(@PathVariable("id") Long id, Model model){
        orderItemService.deleteOrderItem(id);
        return "redirect:/order-items";
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("/{id}")
    public String viewOrderItem(@PathVariable("id") Long id, Model model){
        OrderItem orderItem = orderItemService.getOrderItemById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid orderItem Id:" + id));
        model.addAttribute("orderItem", orderItem);
        return "orderitems/view";
    }
}
