package com.example.onlinestoreapi.service;

import com.example.onlinestoreapi.entity.Role;
import com.example.onlinestoreapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleByName(String name){
        return roleRepository.findById(name)
                .orElseThrow(() -> new IllegalArgumentException("Роль не найдена: " + name));
    }
}
