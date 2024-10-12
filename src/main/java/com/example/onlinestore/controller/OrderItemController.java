package com.example.onlinestore.controller;

import com.example.onlinestore.entity.OrderItem;
import com.example.onlinestore.service.OrderItemService;
import com.example.onlinestore.service.OrderService;
import com.example.onlinestore.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final OrderService orderService;
    private final ProductService productService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService, OrderService orderService, ProductService productService){
        this.orderItemService = orderItemService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<OrderItem> createOrderItem(@Valid @RequestBody OrderItem orderItem){
        if(orderItem.getOrder() == null || orderItem.getOrder().getId() == null ||
                orderItem.getProduct() == null || orderItem.getProduct().getId() == null){
            return ResponseEntity.badRequest().build();
        }
        Optional<com.example.onlinestore.entity.Order> order = orderService.getOrderById(orderItem.getOrder().getId());
        Optional<com.example.onlinestore.entity.Product> product = productService.getProductById(orderItem.getProduct().getId());
        if(order.isEmpty() || product.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        orderItem.setOrder(order.get());
        orderItem.setProduct(product.get());
        OrderItem createdOrderItem = orderItemService.createOrderItem(orderItem);
        return ResponseEntity.ok(createdOrderItem);
    }

    @GetMapping
    public ResponseEntity<List<OrderItem>> getAllOrderItems(){
        List<OrderItem> orderItems = orderItemService.getAllOrderItems();
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id){
        Optional<OrderItem> orderItem = orderItemService.getOrderItemById(id);
        return orderItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id, @Valid @RequestBody OrderItem orderItemDetails){
        try{
            OrderItem updatedOrderItem = orderItemService.updateOrderItem(id, orderItemDetails);
            return ResponseEntity.ok(updatedOrderItem);
        } catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id){
        try{
            orderItemService.deleteOrderItem(id);
            return ResponseEntity.noContent().build();
        } catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}
