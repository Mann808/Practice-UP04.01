package com.mpt.journal.service;

import com.mpt.journal.entity.CourseEntity;
import com.mpt.journal.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<CourseEntity> findAllCourses() {
        return courseRepository.findByIsDeletedFalse();
    }

    @Override
    public CourseEntity findCourseById(int id) {
        return courseRepository.findById(id)
                .filter(course -> !course.isDeleted())
                .orElse(null);
    }

    @Override
    public CourseEntity addCourse(CourseEntity course) {
        course.setDeleted(false);
        return courseRepository.save(course);
    }

    @Override
    public CourseEntity updateCourse(CourseEntity course) {
        CourseEntity existingCourse = findCourseById(course.getId());
        if (existingCourse != null) {
            existingCourse.setCourseName(course.getCourseName());
            existingCourse.setCourseDescription(course.getCourseDescription());
            existingCourse.setFaculty(course.getFaculty());
            existingCourse.setTeachers(course.getTeachers());
            return courseRepository.save(existingCourse);
        }
        return null;
    }

    @Override
    public void deleteCourse(int id) {
        CourseEntity course = findCourseById(id);
        if (course != null) {
            course.setDeleted(true);
            courseRepository.save(course);
        }
    }

    @Override
    public void deleteCoursePhysical(int id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<CourseEntity> findCoursesByName(String courseName) {
        return courseRepository.findByCourseNameContainingIgnoreCaseAndIsDeletedFalse(courseName);
    }

    @Override
    public List<CourseEntity> filterCourses(String courseName, String courseDescription) {
        return courseRepository.findByCourseNameContainingIgnoreCaseAndCourseDescriptionContainingIgnoreCaseAndIsDeletedFalse(courseName, courseDescription);
    }

    @Override
    public void deleteMultipleCourses(List<Integer> ids) {
        List<CourseEntity> courses = courseRepository.findAllById(ids);
        courses.forEach(course -> course.setDeleted(true));
        courseRepository.saveAll(courses);
    }

    @Override
    public List<CourseEntity> findCoursesByPage(int page, int size) {
        return courseRepository.findAll(PageRequest.of(page - 1, size))
                .getContent()
                .stream()
                .filter(course -> !course.isDeleted())
                .toList();
    }
}
