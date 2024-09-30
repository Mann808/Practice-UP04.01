package com.mpt.journal.controller;

import com.mpt.journal.entity.CourseEntity;
import com.mpt.journal.service.CourseService;
import com.mpt.journal.service.FacultyService;
import com.mpt.journal.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/courses")
    public String getAllCourses(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "10") int size,
                                Model model) {
        List<CourseEntity> courses = courseService.findCoursesByPage(page, size);
        model.addAttribute("courses", courses);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "courseList";
    }

    @GetMapping("/courses/add")
    public String showAddCourseForm(Model model) {
        model.addAttribute("course", new CourseEntity());
        model.addAttribute("faculties", facultyService.findAllFaculties());
        model.addAttribute("teachers", teacherService.findAllTeachers());
        return "courseForm";
    }

    @PostMapping("/courses/add")
    public String addCourse(@Valid @ModelAttribute CourseEntity course, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("faculties", facultyService.findAllFaculties());
            model.addAttribute("teachers", teacherService.findAllTeachers());
            return "courseForm";
        }
        courseService.addCourse(course);
        return "redirect:/courses";
    }

    @GetMapping("/courses/edit/{id}")
    public String showEditCourseForm(@PathVariable int id, Model model) {
        CourseEntity course = courseService.findCourseById(id);
        if (course == null) {
            return "redirect:/courses";
        }
        model.addAttribute("course", course);
        model.addAttribute("faculties", facultyService.findAllFaculties());
        model.addAttribute("teachers", teacherService.findAllTeachers());
        return "courseForm";
    }

    @PostMapping("/courses/update")
    public String updateCourse(@Valid @ModelAttribute CourseEntity course, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("faculties", facultyService.findAllFaculties());
            model.addAttribute("teachers", teacherService.findAllTeachers());
            return "courseForm";
        }
        courseService.updateCourse(course);
        return "redirect:/courses";
    }

    @PostMapping("/courses/delete")
    public String deleteCourse(@RequestParam int id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }

    @GetMapping("/courses/search")
    public String searchCourses(@RequestParam String courseName, Model model) {
        List<CourseEntity> courses = courseService.findCoursesByName(courseName);
        model.addAttribute("courses", courses);
        return "courseList";
    }

    @GetMapping("/courses/filter")
    public String filterCourses(@RequestParam(required = false) String courseName,
                                @RequestParam(required = false) String courseDescription,
                                Model model) {
        List<CourseEntity> courses = courseService.filterCourses(courseName, courseDescription);
        model.addAttribute("courses", courses);
        return "courseList";
    }

    @PostMapping("/courses/deletePhysical")
    public String deleteCoursePhysical(@RequestParam int id) {
        courseService.deleteCoursePhysical(id);
        return "redirect:/courses";
    }

    @PostMapping("/courses/deleteMultiple")
    public String deleteMultipleCourses(@RequestParam("ids") List<Integer> ids) {
        courseService.deleteMultipleCourses(ids);
        return "redirect:/courses";
    }
}
