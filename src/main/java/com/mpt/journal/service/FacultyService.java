package com.mpt.journal.service;

import com.mpt.journal.entity.FacultyEntity;
import java.util.List;

public interface FacultyService {
    List<FacultyEntity> findAllFaculties();
    FacultyEntity findFacultyById(int id);
    FacultyEntity addFaculty(FacultyEntity faculty);
    FacultyEntity updateFaculty(FacultyEntity faculty);
    void deleteFaculty(int id); // Логическое удаление
    void deleteFacultyPhysical(int id); // Физическое удаление
    List<FacultyEntity> findFacultiesByName(String name);
    void deleteMultipleFaculties(List<Integer> ids);
    List<FacultyEntity> findFacultiesByPage(int page, int size);
}
