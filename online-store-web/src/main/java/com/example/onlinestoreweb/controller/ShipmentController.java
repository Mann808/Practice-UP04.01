package com.example.onlinestoreweb.controller;

import com.example.onlinestoreweb.dto.Shipment;
import com.example.onlinestoreweb.service.ShipmentService;
import com.example.onlinestoreweb.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;
    private final OrderService orderService;

    public ShipmentController(ShipmentService shipmentService, OrderService orderService) {
        this.shipmentService = shipmentService;
        this.orderService = orderService;
    }

    @GetMapping
    public String listShipments(Model model) {
        model.addAttribute("shipments", shipmentService.getAllShipments());
        return "shipments/list";
    }

    @GetMapping("/{id}")
    public String viewShipment(@PathVariable Long id, Model model) {
        model.addAttribute("shipment", shipmentService.getShipmentById(id));
        return "shipments/view";
    }

    @GetMapping("/new")
    public String newShipmentForm(Model model) {
        model.addAttribute("shipment", new Shipment());
        model.addAttribute("orders", orderService.getAllOrders());
        return "shipments/create";
    }

    @PostMapping
    public String createShipment(@Valid @ModelAttribute Shipment shipment, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("orders", orderService.getAllOrders());
            return "shipments/create";
        }
        shipmentService.createShipment(shipment);
        return "redirect:/shipments";
    }

    @GetMapping("/edit/{id}")
    public String editShipmentForm(@PathVariable Long id, Model model) {
        model.addAttribute("shipment", shipmentService.getShipmentById(id));
        model.addAttribute("orders", orderService.getAllOrders());
        return "shipments/edit";
    }

    @PostMapping("/update/{id}")
    public String updateShipment(@PathVariable Long id, @Valid @ModelAttribute Shipment shipment, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("orders", orderService.getAllOrders());
            return "shipments/edit";
        }
        shipmentService.updateShipment(id, shipment);
        return "redirect:/shipments";
    }

    @GetMapping("/delete/{id}")
    public String deleteShipment(@PathVariable Long id) {
        shipmentService.deleteShipment(id);
        return "redirect:/shipments";
    }
}