package com.mpt.journal.service;

import com.mpt.journal.entity.FacultyEntity;
import com.mpt.journal.repository.FacultyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    @Override
    public List<FacultyEntity> findAllFaculties() {
        return facultyRepository.findByIsDeletedFalse();
    }

    @Override
    public FacultyEntity findFacultyById(int id) {
        return facultyRepository.findById(id)
                .filter(faculty -> !faculty.isDeleted())
                .orElse(null);
    }

    @Override
    public FacultyEntity addFaculty(FacultyEntity faculty) {
        faculty.setDeleted(false);
        return facultyRepository.save(faculty);
    }

    @Override
    public FacultyEntity updateFaculty(FacultyEntity faculty) {
        FacultyEntity existingFaculty = findFacultyById(faculty.getId());
        if (existingFaculty != null) {
            existingFaculty.setName(faculty.getName());
            existingFaculty.setCourses(faculty.getCourses());
            existingFaculty.setDepartment(faculty.getDepartment());
            return facultyRepository.save(existingFaculty);
        }
        return null;
    }

    @Override
    public void deleteFaculty(int id) {
        FacultyEntity faculty = findFacultyById(id);
        if (faculty != null) {
            faculty.setDeleted(true);
            facultyRepository.save(faculty);
        }
    }

    @Override
    public void deleteFacultyPhysical(int id) {
        facultyRepository.deleteById(id);
    }

    @Override
    public List<FacultyEntity> findFacultiesByName(String name) {
        return facultyRepository.findByNameContainingIgnoreCaseAndIsDeletedFalse(name);
    }

    @Override
    public void deleteMultipleFaculties(List<Integer> ids) {
        List<FacultyEntity> faculties = facultyRepository.findAllById(ids);
        faculties.forEach(faculty -> faculty.setDeleted(true));
        facultyRepository.saveAll(faculties);
    }

    @Override
    public List<FacultyEntity> findFacultiesByPage(int page, int size) {
        return facultyRepository.findAll(PageRequest.of(page - 1, size))
                .getContent()
                .stream()
                .filter(faculty -> !faculty.isDeleted())
                .toList();
    }
}
