package com.mpt.journal.controller;

import com.mpt.journal.entity.TeacherEntity;
import com.mpt.journal.service.CourseService;
import com.mpt.journal.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/teachers")
    public String getAllTeachers(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 Model model) {
        List<TeacherEntity> teachers = teacherService.findTeachersByPage(page, size);
        model.addAttribute("teachers", teachers);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "teacherList";
    }

    @GetMapping("/teachers/add")
    public String showAddTeacherForm(Model model) {
        model.addAttribute("teacher", new TeacherEntity());
        model.addAttribute("courses", courseService.findAllCourses());
        return "teacherForm";
    }

    @PostMapping("/teachers/add")
    public String addTeacher(@Valid @ModelAttribute TeacherEntity teacher, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("courses", courseService.findAllCourses());
            return "teacherForm";
        }
        teacherService.addTeacher(teacher);
        return "redirect:/teachers";
    }

    @GetMapping("/teachers/edit/{id}")
    public String showEditTeacherForm(@PathVariable int id, Model model) {
        TeacherEntity teacher = teacherService.findTeacherById(id);
        if (teacher == null) {
            return "redirect:/teachers";
        }
        model.addAttribute("teacher", teacher);
        model.addAttribute("courses", courseService.findAllCourses());
        return "teacherForm";
    }

    @PostMapping("/teachers/update")
    public String updateTeacher(@Valid @ModelAttribute TeacherEntity teacher, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("courses", courseService.findAllCourses());
            return "teacherForm";
        }
        teacherService.updateTeacher(teacher);
        return "redirect:/teachers";
    }

    @PostMapping("/teachers/delete")
    public String deleteTeacher(@RequestParam int id) {
        teacherService.deleteTeacher(id);
        return "redirect:/teachers";
    }

    @GetMapping("/teachers/search")
    public String searchTeachers(@RequestParam String name, Model model) {
        List<TeacherEntity> teachers = teacherService.findTeachersByName(name);
        model.addAttribute("teachers", teachers);
        return "teacherList";
    }

    @PostMapping("/teachers/deletePhysical")
    public String deleteTeacherPhysical(@RequestParam int id) {
        teacherService.deleteTeacherPhysical(id);
        return "redirect:/teachers";
    }

    @PostMapping("/teachers/deleteMultiple")
    public String deleteMultipleTeachers(@RequestParam("ids") List<Integer> ids) {
        teacherService.deleteMultipleTeachers(ids);
        return "redirect:/teachers";
    }
}
