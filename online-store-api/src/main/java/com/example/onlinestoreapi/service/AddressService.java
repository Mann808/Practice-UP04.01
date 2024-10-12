package com.example.onlinestoreapi.service;

import com.example.onlinestoreapi.entity.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {
    Address createAddress(Address address);
    List<Address> getAllAddresses();
    Optional<Address> getAddressById(Long id);
    Address updateAddress(Long id, Address address);
    void deleteAddress(Long id);
}
