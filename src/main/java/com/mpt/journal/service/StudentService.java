package com.mpt.journal.service;

import com.mpt.journal.entity.StudentEntity;
import java.util.List;

public interface StudentService {
    List<StudentEntity> findAllStudents();
    StudentEntity findStudentById(int id);
    StudentEntity addStudent(StudentEntity student);
    StudentEntity updateStudent(StudentEntity student);
    void deleteStudent(int id); // Логическое удаление
    void deleteStudentPhysical(int id); // Физическое удаление
    List<StudentEntity> findStudentsByName(String name);
    List<StudentEntity> filterStudents(String name, String lastName, String firstName);
    void deleteMultipleStudents(List<Integer> ids);
    List<StudentEntity> findStudentsByPage(int page, int size);
    List<StudentEntity> findStudentsByPage(int page, int size, String sortField, String sortDirection);
}
