package com.mpt.journal.controller;

import com.mpt.journal.entity.DepartmentEntity;
import com.mpt.journal.service.DepartmentService;
import com.mpt.journal.service.FacultyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private FacultyService facultyService;

    @GetMapping("/departments")
    public String getAllDepartments(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    Model model) {
        List<DepartmentEntity> departments = departmentService.findDepartmentsByPage(page, size);
        model.addAttribute("departments", departments);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "departmentList";
    }

    @GetMapping("/departments/add")
    public String showAddDepartmentForm(Model model) {
        model.addAttribute("department", new DepartmentEntity());
        model.addAttribute("faculties", facultyService.findAllFaculties());
        return "departmentForm";
    }

    @PostMapping("/departments/add")
    public String addDepartment(@Valid @ModelAttribute DepartmentEntity department, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("faculties", facultyService.findAllFaculties());
            return "departmentForm";
        }
        departmentService.addDepartment(department);
        return "redirect:/departments";
    }

    @GetMapping("/departments/edit/{id}")
    public String showEditDepartmentForm(@PathVariable int id, Model model) {
        DepartmentEntity department = departmentService.findDepartmentById(id);
        if (department == null) {
            return "redirect:/departments";
        }
        model.addAttribute("department", department);
        model.addAttribute("faculties", facultyService.findAllFaculties());
        return "departmentForm";
    }

    @PostMapping("/departments/update")
    public String updateDepartment(@Valid @ModelAttribute DepartmentEntity department, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("faculties", facultyService.findAllFaculties());
            return "departmentForm";
        }
        departmentService.updateDepartment(department);
        return "redirect:/departments";
    }

    @PostMapping("/departments/delete")
    public String deleteDepartment(@RequestParam int id) {
        departmentService.deleteDepartment(id);
        return "redirect:/departments";
    }


    @GetMapping("/departments/search")
    public String searchDepartments(@RequestParam String name, Model model) {
        List<DepartmentEntity> departments = departmentService.findDepartmentsByName(name);
        model.addAttribute("departments", departments);
        return "departmentList";
    }

    @PostMapping("/departments/deletePhysical")
    public String deleteDepartmentPhysical(@RequestParam int id) {
        departmentService.deleteDepartmentPhysical(id);
        return "redirect:/departments";
    }

    @PostMapping("/departments/deleteMultiple")
    public String deleteMultipleDepartments(@RequestParam("ids") List<Integer> ids) {
        departmentService.deleteMultipleDepartments(ids);
        return "redirect:/departments";
    }
}
