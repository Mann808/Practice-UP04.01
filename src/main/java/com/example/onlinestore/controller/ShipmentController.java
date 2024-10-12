package com.example.onlinestore.controller;

import com.example.onlinestore.entity.Shipment;
import com.example.onlinestore.service.ShipmentService;
import com.example.onlinestore.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;
    private final OrderService orderService;

    @Autowired
    public ShipmentController(ShipmentService shipmentService, OrderService orderService){
        this.shipmentService = shipmentService;
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Shipment> createShipment(@Valid @RequestBody Shipment shipment){
        if(shipment.getOrder() == null || shipment.getOrder().getId() == null){
            return ResponseEntity.badRequest().build();
        }
        Optional<com.example.onlinestore.entity.Order> order = orderService.getOrderById(shipment.getOrder().getId());
        if(order.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        shipment.setOrder(order.get());
        Shipment createdShipment = shipmentService.createShipment(shipment);
        return ResponseEntity.ok(createdShipment);
    }

    @GetMapping
    public ResponseEntity<List<Shipment>> getAllShipments(){
        List<Shipment> shipments = shipmentService.getAllShipments();
        return ResponseEntity.ok(shipments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable Long id){
        Optional<Shipment> shipment = shipmentService.getShipmentById(id);
        return shipment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shipment> updateShipment(@PathVariable Long id, @Valid @RequestBody Shipment shipmentDetails){
        try{
            Shipment updatedShipment = shipmentService.updateShipment(id, shipmentDetails);
            return ResponseEntity.ok(updatedShipment);
        } catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long id){
        try{
            shipmentService.deleteShipment(id);
            return ResponseEntity.noContent().build();
        } catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}
