package com.example.onlinestore.service;

import com.example.onlinestore.entity.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierService {
    Supplier createSupplier(Supplier supplier);
    List<Supplier> getAllSuppliers();
    Optional<Supplier> getSupplierById(Long id);
    Supplier updateSupplier(Long id, Supplier supplier);
    void deleteSupplier(Long id);
}
