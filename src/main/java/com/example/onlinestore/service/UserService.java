package com.example.onlinestore.service;

import com.example.onlinestore.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    void saveUser(User user);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
