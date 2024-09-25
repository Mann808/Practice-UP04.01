package com.mpt.journal.service;

import com.mpt.journal.model.StudentModel;
import com.mpt.journal.repository.InMemoryStudentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

// Сервисный слой отвечает за бизнес-логику приложения
@Service
public class InMemoryStudentServiceImpl implements StudentService {

    private final InMemoryStudentRepository studentRepository;

    public InMemoryStudentServiceImpl(InMemoryStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentModel> findAllStudent() {
        return studentRepository.findAllStudents();
    }

    @Override
    public StudentModel findStudentById(int id) {
        return studentRepository.findStudentById(id);
    }

    @Override
    public StudentModel addStudent(StudentModel student) {
        return studentRepository.addStudent(student);
    }

    @Override
    public StudentModel updateStudent(StudentModel student) {
        return studentRepository.updateStudent(student);
    }

    @Override
    public void deleteStudent(int id) {
        studentRepository.deleteStudent(id);
    }

    @Override
    public void deleteStudentPhysical(int id) {
        studentRepository.deleteStudentPhysical(id);
    }

    @Override
    public List<StudentModel> findStudentsByName(String name) {
        return studentRepository.findStudentsByName(name);
    }

    @Override
    public List<StudentModel> filterStudents(String name, String lastName, String firstName) {
        return studentRepository.filterStudents(name, lastName, firstName);
    }

    @Override
    public void deleteMultipleStudents(List<Integer> ids) {
        studentRepository.deleteMultipleStudents(ids);
    }

    @Override
    public List<StudentModel> findStudentsByPage(int page, int size) {
        return studentRepository.findStudentsByPage(page, size);
    }
}
