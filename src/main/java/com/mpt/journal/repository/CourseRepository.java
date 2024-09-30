package com.mpt.journal.repository;

import com.mpt.journal.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Integer> {
    List<CourseEntity> findByIsDeletedFalse();
    List<CourseEntity> findByCourseNameContainingIgnoreCaseAndIsDeletedFalse(String courseName);
    List<CourseEntity> findByCourseNameContainingIgnoreCaseAndCourseDescriptionContainingIgnoreCaseAndIsDeletedFalse(String courseName, String courseDescription);
}
