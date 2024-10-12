package com.example.onlinestore.config;

import com.example.onlinestore.entity.Role;
import com.example.onlinestore.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void init(){
        if(!roleRepository.existsById("ROLE_USER")){
            roleRepository.save(new Role("ROLE_USER"));
        }
        if(!roleRepository.existsById("ROLE_MANAGER")){
            roleRepository.save(new Role("ROLE_MANAGER"));
        }
        if(!roleRepository.existsById("ROLE_ADMIN")){
            roleRepository.save(new Role("ROLE_ADMIN"));
        }
    }
}
