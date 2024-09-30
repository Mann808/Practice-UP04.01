package com.mpt.journal.service;

import com.mpt.journal.entity.StudentEntity;
import com.mpt.journal.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<StudentEntity> findAllStudents() {
        return studentRepository.findByIsDeletedFalse();
    }

    @Override
    public StudentEntity findStudentById(int id) {
        return studentRepository.findById(id)
                .filter(student -> !student.isDeleted())
                .orElse(null);
    }

    @Override
    public StudentEntity addStudent(StudentEntity student) {
        student.setDeleted(false);
        return studentRepository.save(student);
    }

    @Override
    public StudentEntity updateStudent(StudentEntity student) {
        StudentEntity existingStudent = findStudentById(student.getId());
        if (existingStudent != null) {
            existingStudent.setName(student.getName());
            existingStudent.setLastName(student.getLastName());
            existingStudent.setFirstName(student.getFirstName());
            existingStudent.setMiddleName(student.getMiddleName());  // Обновление middleName
            existingStudent.setCourses(student.getCourses());  // Обновление связи с курсами
            return studentRepository.save(existingStudent);
        }
        return null;
    }

    @Override
    public void deleteStudent(int id) {
        StudentEntity student = findStudentById(id);
        if (student != null) {
            student.setDeleted(true);  // Логическое удаление
            studentRepository.save(student);
        }
    }

    @Override
    public void deleteStudentPhysical(int id) {
        studentRepository.deleteById(id);  // Физическое удаление
    }

    @Override
    public List<StudentEntity> findStudentsByName(String name) {
        return studentRepository.findByNameContainingIgnoreCaseAndIsDeletedFalse(name);
    }

    @Override
    public List<StudentEntity> filterStudents(String name, String lastName, String firstName) {
        return studentRepository.findByNameContainingIgnoreCaseAndLastNameContainingIgnoreCaseAndFirstNameContainingIgnoreCaseAndIsDeletedFalse(name, lastName, firstName);
    }

    @Override
    public void deleteMultipleStudents(List<Integer> ids) {
        List<StudentEntity> students = studentRepository.findAllById(ids);
        students.forEach(student -> student.setDeleted(true));  // Логическое удаление
        studentRepository.saveAll(students);
    }

    @Override
    public List<StudentEntity> findStudentsByPage(int page, int size) {
        return studentRepository.findAll(PageRequest.of(page - 1, size))
                .getContent()
                .stream()
                .filter(student -> !student.isDeleted())  // Исключаем удалённых студентов
                .toList();
    }

    @Override
    public List<StudentEntity> findStudentsByPage(int page, int size, String sortField, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        return studentRepository.findAll(PageRequest.of(page - 1, size, sort))
                .getContent()
                .stream()
                .filter(student -> !student.isDeleted())
                .toList();
    }
}
