package com.mpt.journal.repository;

import com.mpt.journal.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Integer> {
    List<TeacherEntity> findByIsDeletedFalse();
    List<TeacherEntity> findByNameContainingIgnoreCaseAndIsDeletedFalse(String name);
}
