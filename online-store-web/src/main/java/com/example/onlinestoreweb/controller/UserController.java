package com.example.onlinestoreweb.controller;

import com.example.onlinestoreweb.dto.User;
import com.example.onlinestoreweb.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/list";
    }

    @GetMapping("/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/view";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users/create";
    }

    @PostMapping
    public String createUser(@Valid @ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) {
            return "users/create";
        }
        userService.createUser(user);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/edit";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @Valid @ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) {
            return "users/edit";
        }
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}