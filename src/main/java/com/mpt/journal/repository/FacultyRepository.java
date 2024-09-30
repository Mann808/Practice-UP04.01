package com.mpt.journal.repository;

import com.mpt.journal.entity.FacultyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<FacultyEntity, Integer> {
    List<FacultyEntity> findByIsDeletedFalse();
    List<FacultyEntity> findByNameContainingIgnoreCaseAndIsDeletedFalse(String name);
}
