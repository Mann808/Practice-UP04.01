package com.example.onlinestore.service;

import com.example.onlinestore.entity.Role;
import com.example.onlinestore.repository.RoleRepository;
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
