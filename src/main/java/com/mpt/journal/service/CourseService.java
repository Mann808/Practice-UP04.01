package com.mpt.journal.service;

import com.mpt.journal.entity.CourseEntity;
import java.util.List;

public interface CourseService {
    List<CourseEntity> findAllCourses();
    CourseEntity findCourseById(int id);
    CourseEntity addCourse(CourseEntity course);
    CourseEntity updateCourse(CourseEntity course);
    void deleteCourse(int id); // Логическое удаление
    void deleteCoursePhysical(int id); // Физическое удаление
    List<CourseEntity> findCoursesByName(String courseName);
    List<CourseEntity> filterCourses(String courseName, String courseDescription);
    void deleteMultipleCourses(List<Integer> ids);
    List<CourseEntity> findCoursesByPage(int page, int size);
}
