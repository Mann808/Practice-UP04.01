package com.example.onlinestoreapi.repository;

import com.example.onlinestoreapi.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{
}
