package com.example.onlinestoreapi.service;

import com.example.onlinestoreapi.entity.Address;
import com.example.onlinestoreapi.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    public Address updateAddress(Long id, Address addressDetails) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found with id " + id));

        address.setStreet(addressDetails.getStreet());
        address.setCity(addressDetails.getCity());
        address.setState(addressDetails.getState());
        address.setPostalCode(addressDetails.getPostalCode());
        address.setCountry(addressDetails.getCountry());
        address.setUser(addressDetails.getUser());

        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found with id " + id));
        addressRepository.delete(address);
    }
}