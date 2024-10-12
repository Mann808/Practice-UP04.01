package com.example.onlinestoreweb.controller;

import com.example.onlinestoreweb.dto.Supplier;
import com.example.onlinestoreweb.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public String listSuppliers(Model model) {
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "suppliers/list";
    }

    @GetMapping("/{id}")
    public String viewSupplier(@PathVariable Long id, Model model) {
        model.addAttribute("supplier", supplierService.getSupplierById(id));
        return "suppliers/view";
    }

    @GetMapping("/new")
    public String newSupplierForm(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "suppliers/create";
    }

    @PostMapping
    public String createSupplier(@Valid @ModelAttribute Supplier supplier, BindingResult result) {
        if (result.hasErrors()) {
            return "suppliers/create";
        }
        supplierService.createSupplier(supplier);
        return "redirect:/suppliers";
    }

    @GetMapping("/edit/{id}")
    public String editSupplierForm(@PathVariable Long id, Model model) {
        model.addAttribute("supplier", supplierService.getSupplierById(id));
        return "suppliers/edit";
    }

    @PostMapping("/update/{id}")
    public String updateSupplier(@PathVariable Long id, @Valid @ModelAttribute Supplier supplier, BindingResult result) {
        if (result.hasErrors()) {
            return "suppliers/edit";
        }
        supplierService.updateSupplier(id, supplier);
        return "redirect:/suppliers";
    }

    @GetMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return "redirect:/suppliers";
    }
}