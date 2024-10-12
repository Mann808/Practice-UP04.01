package com.example.onlinestoreapi.service;

import com.example.onlinestoreapi.entity.Shipment;
import com.example.onlinestoreapi.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;

    @Autowired
    public ShipmentServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public Shipment createShipment(Shipment shipment) {
        return shipmentRepository.save(shipment);
    }

    @Override
    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    @Override
    public Optional<Shipment> getShipmentById(Long id) {
        return shipmentRepository.findById(id);
    }

    @Override
    public Shipment updateShipment(Long id, Shipment shipmentDetails) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id " + id));

        shipment.setShipmentMethod(shipmentDetails.getShipmentMethod());
        shipment.setShipmentDate(shipmentDetails.getShipmentDate());
        shipment.setOrder(shipmentDetails.getOrder());

        return shipmentRepository.save(shipment);
    }

    @Override
    public void deleteShipment(Long id) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id " + id));
        shipmentRepository.delete(shipment);
    }
}