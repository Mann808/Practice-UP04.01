package com.example.onlinestore.controller;

import com.example.onlinestore.entity.Supplier;
import com.example.onlinestore.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/suppliers")
public class ViewSupplierController {

    private final SupplierService supplierService;

    @Autowired
    public ViewSupplierController(SupplierService supplierService){
        this.supplierService = supplierService;
    }

    // Добавлено
    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping
    public String listSuppliers(Model model){
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        model.addAttribute("suppliers", suppliers);
        return "suppliers/list";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/new")
    public String showCreateForm(Model model){
        model.addAttribute("supplier", new Supplier());
        return "suppliers/create";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping
    public String createSupplier(@Valid @ModelAttribute("supplier") Supplier supplier, BindingResult result, Model model){
        if(result.hasErrors()){
            return "suppliers/create";
        }
        supplierService.createSupplier(supplier);
        return "redirect:/suppliers";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model){
        Supplier supplier = supplierService.getSupplierById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid supplier Id:" + id));
        model.addAttribute("supplier", supplier);
        return "suppliers/edit";
    }

    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/update/{id}")
    public String updateSupplier(@PathVariable("id") Long id, @Valid @ModelAttribute("supplier") Supplier supplier, BindingResult result, Model model){
        if(result.hasErrors()){
            supplier.setId(id);
            return "suppliers/edit";
        }
        supplierService.updateSupplier(id, supplier);
        return "redirect:/suppliers";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable("id") Long id, Model model){
        supplierService.deleteSupplier(id);
        return "redirect:/suppliers";
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER', 'ADMIN')")
    @GetMapping("/{id}")
    public String viewSupplier(@PathVariable("id") Long id, Model model){
        Supplier supplier = supplierService.getSupplierById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid supplier Id:" + id));
        model.addAttribute("supplier", supplier);
        return "suppliers/view";
    }
}
