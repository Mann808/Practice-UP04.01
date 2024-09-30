package com.mpt.journal.repository;

import com.mpt.journal.model.StudentModel;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

// Репозиторий отвечает за хранение и управление данными студентов в памяти
@Repository
public class InMemoryStudentRepository {
    private List<StudentModel> students = new ArrayList<>();
    private AtomicInteger idCounter = new AtomicInteger(1); // Генерация уникального ID

    public StudentModel addStudent(StudentModel student) {
        student.setId(idCounter.getAndIncrement()); // Установка уникального ID
        students.add(student);
        return student;
    }

    public StudentModel updateStudent(StudentModel student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == student.getId()) {
                students.set(i, student);
                return student;
            }
        }
        return null; // Студент не найден
    }

    // Логическое удаление
    public void deleteStudent(int id) {
        students.stream()
                .filter(student -> student.getId() == id)
                .findFirst()
                .ifPresent(student -> student.setDeleted(true));
    }

    // Физическое удаление
    public void deleteStudentPhysical(int id) {
        students.removeIf(student -> student.getId() == id);
    }

    public List<StudentModel> findAllStudents() {
        return students.stream()
                .filter(student -> !student.isDeleted())
                .collect(Collectors.toList());
    }

    public StudentModel findStudentById(int id) {
        return students.stream()
                .filter(student -> student.getId() == id && !student.isDeleted())
                .findFirst()
                .orElse(null);
    }

    // Поиск студентов по имени
    public List<StudentModel> findStudentsByName(String name) {
        return students.stream()
                .filter(student -> student.getName().equalsIgnoreCase(name) && !student.isDeleted())
                .collect(Collectors.toList());
    }

    // Фильтрация студентов по критериям
    public List<StudentModel> filterStudents(String name, String lastName, String firstName) {
        return students.stream()
                .filter(student -> !student.isDeleted())
                .filter(student -> name == null || student.getName().equalsIgnoreCase(name))
                .filter(student -> lastName == null || student.getLastName().equalsIgnoreCase(lastName))
                .filter(student -> firstName == null || student.getFirstName().equalsIgnoreCase(firstName))
                .collect(Collectors.toList());
    }

    // Множественное удаление
    public void deleteMultipleStudents(List<Integer> ids) {
        students.stream()
                .filter(student -> ids.contains(student.getId()))
                .forEach(student -> student.setDeleted(true));
    }

    // Пагинация
    public List<StudentModel> findStudentsByPage(int page, int size) {
        return students.stream()
                .filter(student -> !student.isDeleted())
                .skip((page - 1) * size)
                .limit(size)
                .collect(Collectors.toList());
    }
}
