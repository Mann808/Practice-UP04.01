package com.mpt.journal.controller;

import com.mpt.journal.model.StudentModel;
import com.mpt.journal.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Основная бизнес-логика нашего проекта
@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Получение всех студентов с пагинацией
    @GetMapping("/students")
    public String getAllStudents(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 Model model) {
        if (size < 10) {
            size = 10; // Минимальный размер страницы
        }
        List<StudentModel> students = studentService.findStudentsByPage(page, size);
        model.addAttribute("students", students);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "studentList";
    }

    // Добавление нового студента
    @PostMapping("/students/add")
    public String addStudent(@RequestParam String name,
                             @RequestParam String lastName,
                             @RequestParam String firstName,
                             @RequestParam String middleName) {
        StudentModel newStudent = new StudentModel(0, name, lastName, firstName, middleName);
        studentService.addStudent(newStudent);
        return "redirect:/students";
    }

    // Обновление данных студента
    @PostMapping("/students/update")
    public String updateStudent(@RequestParam int id,
                                @RequestParam String name,
                                @RequestParam String lastName,
                                @RequestParam String firstName,
                                @RequestParam String middleName) {
        StudentModel updatedStudent = new StudentModel(id, name, lastName, firstName, middleName);
        studentService.updateStudent(updatedStudent);
        return "redirect:/students";
    }

    // Логическое удаление студента
    @PostMapping("/students/delete")
    public String deleteStudent(@RequestParam int id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

    // Физическое удаление студента
    @PostMapping("/students/deletePhysical")
    public String deleteStudentPhysical(@RequestParam int id) {
        studentService.deleteStudentPhysical(id);
        return "redirect:/students";
    }

    // Поиск студентов по имени
    @GetMapping("/students/search")
    public String searchStudents(@RequestParam String name, Model model) {
        List<StudentModel> students = studentService.findStudentsByName(name);
        model.addAttribute("students", students);
        return "studentList";
    }

    // Фильтрация студентов по критериям
    @GetMapping("/students/filter")
    public String filterStudents(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String lastName,
                                 @RequestParam(required = false) String firstName,
                                 Model model) {
        List<StudentModel> students = studentService.filterStudents(name, lastName, firstName);
        model.addAttribute("students", students);
        return "studentList";
    }

    // Множественное удаление студентов
    @PostMapping("/students/deleteMultiple")
    public String deleteMultipleStudents(@RequestParam("ids") List<Integer> ids) {
        studentService.deleteMultipleStudents(ids);
        return "redirect:/students";
    }

}
