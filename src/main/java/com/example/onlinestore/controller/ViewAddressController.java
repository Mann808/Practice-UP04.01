package com.example.onlinestore.controller;

import com.example.onlinestore.entity.Address;
import com.example.onlinestore.service.AddressService;
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
@RequestMapping("/addresses")
public class ViewAddressController {

    private final AddressService addressService;
    private final UserService userService;

    @Autowired
    public ViewAddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    // Показать список адресов
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping
    public String listAddresses(Model model) {
        List<Address> addresses = addressService.getAllAddresses();
        model.addAttribute("addresses", addresses);
        return "addresses/list";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("address", new Address());
        model.addAttribute("users", userService.getAllUsers());
        return "addresses/create";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping
    public String createAddress(@Valid @ModelAttribute("address") Address address, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("users", userService.getAllUsers());
            return "addresses/create";
        }
        addressService.createAddress(address);
        return "redirect:/addresses";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Address address = addressService.getAddressById(id)
                .orElseThrow(() -> new IllegalArgumentException("Неверный ID адреса: " + id));
        model.addAttribute("address", address);
        model.addAttribute("users", userService.getAllUsers());
        return "addresses/edit";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/update/{id}")
    public String updateAddress(@PathVariable("id") Long id, @Valid @ModelAttribute("address") Address address, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("users", userService.getAllUsers());
            return "addresses/edit";
        }
        addressService.updateAddress(id, address);
        return "redirect:/addresses";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteAddress(@PathVariable("id") Long id, Model model) {
        addressService.deleteAddress(id);
        return "redirect:/addresses";
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("/{id}")
    public String viewAddress(@PathVariable("id") Long id, Model model) {
        Address address = addressService.getAddressById(id)
                .orElseThrow(() -> new IllegalArgumentException("Неверный ID адреса: " + id));
        model.addAttribute("address", address);
        return "addresses/view";
    }
}
