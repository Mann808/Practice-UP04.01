package com.mpt.journal.repository;

import com.mpt.journal.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {
    List<DepartmentEntity> findByIsDeletedFalse();
    List<DepartmentEntity> findByNameContainingIgnoreCaseAndIsDeletedFalse(String name);
}
