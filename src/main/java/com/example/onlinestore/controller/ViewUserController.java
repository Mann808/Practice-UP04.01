package com.example.onlinestore.controller;

import com.example.onlinestore.entity.User;
import com.example.onlinestore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class ViewUserController {

    private final UserService userService;

    @Autowired
    public ViewUserController(UserService userService){
        this.userService = userService;
    }

    // Добавлено
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public String listUsers(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/list";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/new")
    public String showCreateForm(Model model){
        model.addAttribute("user", new User());
        return "users/create";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public String createUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){
        if(result.hasErrors()){
            return "users/create";
        }
        userService.createUser(user);
        return "redirect:/users";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        User user = userService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "users/edit";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @Valid @ModelAttribute("user") User user, BindingResult result, Model model){
        if(result.hasErrors()){
            user.setId(id);
            return "users/edit";
        }
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model){
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    public String viewUser(@PathVariable("id") Long id, Model model){
        User user = userService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "users/view";
    }
}
