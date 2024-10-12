package com.example.onlinestore.controller;

import com.example.onlinestore.entity.Role;
import com.example.onlinestore.entity.User;
import com.example.onlinestore.service.RoleService;
import com.example.onlinestore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Показать форму регистрации
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "auth/register";
    }

    // Обработать регистрацию
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult result,
                               @RequestParam("role") String roleName,
                               Model model){
        if(result.hasErrors()){
            model.addAttribute("roles", roleService.getAllRoles());
            return "auth/register";
        }

        if(userService.existsByEmail(user.getEmail())){
            result.rejectValue("email", null, "Пользователь с таким email уже существует");
            model.addAttribute("roles", roleService.getAllRoles());
            return "auth/register";
        }

        // Установка роли
        Role role = roleService.getRoleByName(roleName);
        user.setRoles(Set.of(role));

        // Шифрование пароля
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Сохранение пользователя
        userService.saveUser(user);

        return "redirect:/login?registerSuccess";
    }

    // Показать форму входа
    @GetMapping("/login")
    public String showLoginForm(){
        return "auth/login";
    }
}
