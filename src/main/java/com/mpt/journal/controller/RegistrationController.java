package com.mpt.journal.controller;

import com.mpt.journal.entity.UserEntity;
import com.mpt.journal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid UserEntity user, BindingResult result, Model model) {
        UserEntity existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null) {
            result.rejectValue("username", null, "Имя пользователя уже существует");
        }

        if (result.hasErrors()) {
            // Объект 'user' уже привязан через @ModelAttribute
            return "register";
        }

        userService.save(user);
        return "redirect:/login";
    }
}
