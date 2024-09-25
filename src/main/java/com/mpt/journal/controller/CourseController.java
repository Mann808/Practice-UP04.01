package com.mpt.journal.controller;

import com.mpt.journal.model.CourseModel;
import com.mpt.journal.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/courses")
    public String getAllCourses(Model model) {
        model.addAttribute("courses", courseService.findAllCourses());
        return "courseList";
    }

    @PostMapping("/courses/add")
    public String addCourse(@RequestParam String courseName,
                            @RequestParam String courseDescription) {
        CourseModel newCourse = new CourseModel(0, courseName, courseDescription);
        courseService.addCourse(newCourse);
        return "redirect:/courses";
    }

    @PostMapping("/courses/delete")
    public String deleteCourse(@RequestParam int id) {
        courseService.deleteCourse(id);
        return "redirect:/courses";
    }

    @PostMapping("/courses/update")
    public String updateCourse(@RequestParam int id,
                               @RequestParam String courseName,
                               @RequestParam String courseDescription) {
        CourseModel updatedCourse = new CourseModel(id, courseName, courseDescription);
        courseService.updateCourse(updatedCourse);
        return "redirect:/courses";
    }
}
