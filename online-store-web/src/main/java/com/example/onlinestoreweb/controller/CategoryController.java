package com.example.onlinestoreweb.controller;

import com.example.onlinestoreweb.dto.Category;
import com.example.onlinestoreweb.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories/list";
    }

    @GetMapping("/{id}")
    public String viewCategory(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "categories/view";
    }

    @GetMapping("/new")
    public String newCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "categories/create";
    }

    @PostMapping
    public String createCategory(@Valid @ModelAttribute Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "categories/create";
        }
        categoryService.createCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "categories/edit";
    }

    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable Long id, @Valid @ModelAttribute Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "categories/edit";
        }
        categoryService.updateCategory(id, category);
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories";
    }
}