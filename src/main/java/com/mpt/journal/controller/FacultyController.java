package com.mpt.journal.controller;

import com.mpt.journal.entity.FacultyEntity;
import com.mpt.journal.service.CourseService;
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
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/faculties")
    public String getAllFaculties(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  Model model) {
        List<FacultyEntity> faculties = facultyService.findFacultiesByPage(page, size);
        model.addAttribute("faculties", faculties);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "facultyList";
    }

    @GetMapping("/faculties/add")
    public String showAddFacultyForm(Model model) {
        model.addAttribute("faculty", new FacultyEntity());
        model.addAttribute("courses", courseService.findAllCourses());
        model.addAttribute("departments", departmentService.findAllDepartments());
        return "facultyForm";
    }

    @PostMapping("/faculties/add")
    public String addFaculty(@Valid @ModelAttribute FacultyEntity faculty, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("courses", courseService.findAllCourses());
            model.addAttribute("departments", departmentService.findAllDepartments());
            return "facultyForm";
        }
        facultyService.addFaculty(faculty);
        return "redirect:/faculties";
    }

    @GetMapping("/faculties/edit/{id}")
    public String showEditFacultyForm(@PathVariable int id, Model model) {
        FacultyEntity faculty = facultyService.findFacultyById(id);
        if (faculty == null) {
            return "redirect:/faculties";
        }
        model.addAttribute("faculty", faculty);
        model.addAttribute("courses", courseService.findAllCourses());
        model.addAttribute("departments", departmentService.findAllDepartments());
        return "facultyForm";
    }

    @PostMapping("/faculties/update")
    public String updateFaculty(@Valid @ModelAttribute FacultyEntity faculty, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("courses", courseService.findAllCourses());
            model.addAttribute("departments", departmentService.findAllDepartments());
            return "facultyForm";
        }
        facultyService.updateFaculty(faculty);
        return "redirect:/faculties";
    }

    @PostMapping("/faculties/delete")
    public String deleteFaculty(@RequestParam int id) {
        facultyService.deleteFaculty(id);
        return "redirect:/faculties";
    }

    @GetMapping("/faculties/search")
    public String searchFaculties(@RequestParam String name, Model model) {
        List<FacultyEntity> faculties = facultyService.findFacultiesByName(name);
        model.addAttribute("faculties", faculties);
        return "facultyList";
    }

    @PostMapping("/faculties/deletePhysical")
    public String deleteFacultyPhysical(@RequestParam int id) {
        facultyService.deleteFacultyPhysical(id);
        return "redirect:/faculties";
    }

    @PostMapping("/faculties/deleteMultiple")
    public String deleteMultipleFaculties(@RequestParam("ids") List<Integer> ids) {
        facultyService.deleteMultipleFaculties(ids);
        return "redirect:/faculties";
    }
}
