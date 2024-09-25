package com.mpt.journal.service;

import com.mpt.journal.model.CourseModel;

import java.util.List;

public interface CourseService {
    List<CourseModel> findAllCourses();
    CourseModel findCourseById(int id);
    CourseModel addCourse(CourseModel course);
    CourseModel updateCourse(CourseModel course);
    void deleteCourse(int id);
}
