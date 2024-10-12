package com.example.onlinestoreapi.service;

import com.example.onlinestoreapi.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role getRoleByName(String name);
}