package com.mpt.journal.repository;

import com.mpt.journal.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
    List<StudentEntity> findByIsDeletedFalse();
    List<StudentEntity> findByNameContainingIgnoreCaseAndIsDeletedFalse(String name);
    List<StudentEntity> findByNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndFirstNameContainingIgnoreCaseAndIsDeletedFalse(
            String name, String lastName, String firstName);
}
