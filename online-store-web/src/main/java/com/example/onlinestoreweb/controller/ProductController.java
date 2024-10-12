package com.example.onlinestoreweb.controller;

import com.example.onlinestoreweb.dto.Product;
import com.example.onlinestoreweb.service.ProductService;
import com.example.onlinestoreweb.service.CategoryService;
import com.example.onlinestoreweb.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final SupplierService supplierService;

    public ProductController(ProductService productService, CategoryService categoryService, SupplierService supplierService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.supplierService = supplierService;
    }

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products/list";
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "products/view";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "products/create";
    }

    @PostMapping
    public String createProduct(@Valid @ModelAttribute Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("suppliers", supplierService.getAllSuppliers());
            return "products/create";
        }
        productService.createProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "products/edit";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("suppliers", supplierService.getAllSuppliers());
            return "products/edit";
        }
        productService.updateProduct(id, product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}