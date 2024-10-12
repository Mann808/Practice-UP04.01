package com.example.onlinestore.service;

import com.example.onlinestore.entity.Role;
import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();
    Role getRoleByName(String name);
}