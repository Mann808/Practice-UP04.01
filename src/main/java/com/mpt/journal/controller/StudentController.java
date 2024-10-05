package com.mpt.journal.controller;

import com.mpt.journal.entity.StudentEntity;
import com.mpt.journal.entity.CourseEntity;
import com.mpt.journal.service.StudentService;
import com.mpt.journal.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/students")
    public String getAllStudents(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String sortField,
                                 @RequestParam(defaultValue = "asc") String sortDirection,
                                 Model model) {
        List<StudentEntity> students = studentService.findStudentsByPage(page, size, sortField, sortDirection);
        model.addAttribute("students", students);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        return "studentList";
    }

    @GetMapping("/students/add")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new StudentEntity());
        model.addAttribute("courses", courseService.findAllCourses());
        return "studentForm";
    }

    @PostMapping("/students/add")
    public String addStudent(@Valid @ModelAttribute StudentEntity student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("courses", courseService.findAllCourses());
            return "studentForm";
        }
        studentService.addStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/students/update")
    public String showUpdateStudentForm(@RequestParam("id") int id, Model model) {
        StudentEntity student = studentService.findStudentById(id);
        if (student == null) {
            return "redirect:/students";
        }
        model.addAttribute("student", student);
        model.addAttribute("courses", courseService.findAllCourses());
        return "studentForm";
    }

    @PostMapping("/students/update")
    public String updateStudent(@Valid @ModelAttribute StudentEntity student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("courses", courseService.findAllCourses());
            return "studentForm";
        }
        studentService.updateStudent(student);
        return "redirect:/students";
    }

    @PostMapping("/students/delete")
    public String deleteStudent(@RequestParam int id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

    @PostMapping("/students/deletePhysical")
    public String deleteStudentPhysical(@RequestParam int id) {
        studentService.deleteStudentPhysical(id);
        return "redirect:/students";
    }

    @GetMapping("/students/search")
    public String searchStudents(@RequestParam String name, Model model) {
        List<StudentEntity> students = studentService.findStudentsByName(name);
        model.addAttribute("students", students);
        return "studentList";
    }

    @GetMapping("/students/filter")
    public String filterStudents(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String lastName,
                                 @RequestParam(required = false) String firstName,
                                 Model model) {
        List<StudentEntity> students = studentService.filterStudents(name, lastName, firstName);
        model.addAttribute("students", students);
        return "studentList";
    }

    @PostMapping("/students/deleteMultiple")
    public String deleteMultipleStudents(@RequestParam("ids") List<Integer> ids) {
        studentService.deleteMultipleStudents(ids);
        return "redirect:/students";
    }
}