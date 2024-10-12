package com.example.onlinestore.controller;

import com.example.onlinestore.entity.Shipment;
import com.example.onlinestore.entity.Order;
import com.example.onlinestore.service.ShipmentService;
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
@RequestMapping("/shipments")
public class ViewShipmentController {

    private final ShipmentService shipmentService;
    private final OrderService orderService;

    @Autowired
    public ViewShipmentController(ShipmentService shipmentService, OrderService orderService){
        this.shipmentService = shipmentService;
        this.orderService = orderService;
    }

    // Добавлено
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping
    public String listShipments(Model model){
        List<Shipment> shipments = shipmentService.getAllShipments();
        model.addAttribute("shipments", shipments);
        return "shipments/list";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/new")
    public String showCreateForm(Model model){
        model.addAttribute("shipment", new Shipment());
        model.addAttribute("orders", orderService.getAllOrders());
        return "shipments/create";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping
    public String createShipment(@Valid @ModelAttribute("shipment") Shipment shipment, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("orders", orderService.getAllOrders());
            return "shipments/create";
        }
        shipmentService.createShipment(shipment);
        return "redirect:/shipments";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        Shipment shipment = shipmentService.getShipmentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid shipment Id:" + id));
        model.addAttribute("shipment", shipment);
        model.addAttribute("orders", orderService.getAllOrders());
        return "shipments/edit";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/update/{id}")
    public String updateShipment(@PathVariable("id") Long id, @Valid @ModelAttribute("shipment") Shipment shipment, BindingResult result, Model model){
        if(result.hasErrors()){
            shipment.setId(id);
            model.addAttribute("orders", orderService.getAllOrders());
            return "shipments/edit";
        }
        shipmentService.updateShipment(id, shipment);
        return "redirect:/shipments";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteShipment(@PathVariable("id") Long id, Model model){
        shipmentService.deleteShipment(id);
        return "redirect:/shipments";
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("/{id}")
    public String viewShipment(@PathVariable("id") Long id, Model model){
        Shipment shipment = shipmentService.getShipmentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid shipment Id:" + id));
        model.addAttribute("shipment", shipment);
        return "shipments/view";
    }
}
