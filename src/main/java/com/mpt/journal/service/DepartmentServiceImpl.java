package com.mpt.journal.service;

import com.mpt.journal.entity.DepartmentEntity;
import com.mpt.journal.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentEntity> findAllDepartments() {
        return departmentRepository.findByIsDeletedFalse();
    }

    @Override
    public DepartmentEntity findDepartmentById(int id) {
        return departmentRepository.findById(id)
                .filter(department -> !department.isDeleted())
                .orElse(null);
    }

    @Override
    public DepartmentEntity addDepartment(DepartmentEntity department) {
        department.setDeleted(false);
        return departmentRepository.save(department);
    }

    @Override
    public DepartmentEntity updateDepartment(DepartmentEntity department) {
        DepartmentEntity existingDepartment = findDepartmentById(department.getId());
        if (existingDepartment != null) {
            existingDepartment.setName(department.getName());
            existingDepartment.setFaculty(department.getFaculty());
            return departmentRepository.save(existingDepartment);
        }
        return null;
    }

    @Override
    public void deleteDepartment(int id) {
        DepartmentEntity department = findDepartmentById(id);
        if (department != null) {
            department.setDeleted(true);
            departmentRepository.save(department);
        }
    }

    @Override
    public void deleteDepartmentPhysical(int id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public List<DepartmentEntity> findDepartmentsByName(String name) {
        return departmentRepository.findByNameContainingIgnoreCaseAndIsDeletedFalse(name);
    }

    @Override
    public void deleteMultipleDepartments(List<Integer> ids) {
        List<DepartmentEntity> departments = departmentRepository.findAllById(ids);
        departments.forEach(department -> department.setDeleted(true));
        departmentRepository.saveAll(departments);
    }

    @Override
    public List<DepartmentEntity> findDepartmentsByPage(int page, int size) {
        return departmentRepository.findAll(PageRequest.of(page - 1, size))
                .getContent()
                .stream()
                .filter(department -> !department.isDeleted())
                .toList();
    }
}
