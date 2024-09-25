package com.mpt.journal.service;

import com.mpt.journal.model.StudentModel;
import java.util.List;

public interface StudentService {
    List<StudentModel> findAllStudent();
    StudentModel findStudentById(int id);
    StudentModel addStudent(StudentModel student);
    StudentModel updateStudent(StudentModel student);
    void deleteStudent(int id); // Логическое удаление
    void deleteStudentPhysical(int id); // Физическое удаление
    List<StudentModel> findStudentsByName(String name);
    List<StudentModel> filterStudents(String name, String lastName, String firstName);
    void deleteMultipleStudents(List<Integer> ids);
    List<StudentModel> findStudentsByPage(int page, int size);
}
