package com.example.onlinestoreweb.controller;

import com.example.onlinestoreweb.dto.Address;
import com.example.onlinestoreweb.service.AddressService;
import com.example.onlinestoreweb.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;
    private final UserService userService;

    public AddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    @GetMapping
    public String listAddresses(Model model) {
        model.addAttribute("addresses", addressService.getAllAddresses());
        return "addresses/list";
    }

    @GetMapping("/{id}")
    public String viewAddress(@PathVariable Long id, Model model) {
        model.addAttribute("address", addressService.getAddressById(id));
        return "addresses/view";
    }

    @GetMapping("/new")
    public String newAddressForm(Model model) {
        model.addAttribute("address", new Address());
        model.addAttribute("users", userService.getAllUsers());
        return "addresses/create";
    }

    @PostMapping
    public String createAddress(@Valid @ModelAttribute Address address, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("users", userService.getAllUsers());
            return "addresses/create";
        }
        addressService.createAddress(address);
        return "redirect:/addresses";
    }

    @GetMapping("/edit/{id}")
    public String editAddressForm(@PathVariable Long id, Model model) {
        Address address = addressService.getAddressById(id);
        model.addAttribute("address", address);
        model.addAttribute("users", userService.getAllUsers());
        return "addresses/edit";
    }

    @PostMapping("/update/{id}")
    public String updateAddress(@PathVariable Long id, @Valid @ModelAttribute Address address, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("users", userService.getAllUsers());
            return "addresses/edit";
        }
        addressService.updateAddress(id, address);
        return "redirect:/addresses";
    }

    @GetMapping("/delete/{id}")
    public String deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return "redirect:/addresses";
    }
}