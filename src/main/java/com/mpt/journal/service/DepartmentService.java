package com.mpt.journal.service;

import com.mpt.journal.entity.DepartmentEntity;
import java.util.List;

public interface DepartmentService {
    List<DepartmentEntity> findAllDepartments();
    DepartmentEntity findDepartmentById(int id);
    DepartmentEntity addDepartment(DepartmentEntity department);
    DepartmentEntity updateDepartment(DepartmentEntity department);
    void deleteDepartment(int id); // Логическое удаление
    void deleteDepartmentPhysical(int id); // Физическое удаление
    List<DepartmentEntity> findDepartmentsByName(String name);
    void deleteMultipleDepartments(List<Integer> ids);
    List<DepartmentEntity> findDepartmentsByPage(int page, int size);
}
