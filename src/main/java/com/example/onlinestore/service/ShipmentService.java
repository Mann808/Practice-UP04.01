package com.example.onlinestore.service;

import com.example.onlinestore.entity.Shipment;

import java.util.List;
import java.util.Optional;

public interface ShipmentService {
    Shipment createShipment(Shipment shipment);
    List<Shipment> getAllShipments();
    Optional<Shipment> getShipmentById(Long id);
    Shipment updateShipment(Long id, Shipment shipment);
    void deleteShipment(Long id);
}
