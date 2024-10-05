package com.mpt.journal.service;

import com.mpt.journal.entity.TeacherEntity;
import com.mpt.journal.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public List<TeacherEntity> findAllTeachers() {
        return teacherRepository.findByIsDeletedFalse();
    }

    @Override
    public TeacherEntity findTeacherById(int id) {
        return teacherRepository.findById(id)
                .filter(teacher -> !teacher.isDeleted())
                .orElse(null);
    }

    @Override
    public TeacherEntity addTeacher(TeacherEntity teacher) {
        teacher.setDeleted(false);
        return teacherRepository.save(teacher);
    }

    @Override
    public TeacherEntity updateTeacher(TeacherEntity teacher) {
        TeacherEntity existingTeacher = findTeacherById(teacher.getId());
        if (existingTeacher != null) {
            existingTeacher.setName(teacher.getName());
            existingTeacher.setCourses(teacher.getCourses());
            return teacherRepository.save(existingTeacher);
        }
        return null;
    }

    @Override
    public void deleteTeacher(int id) {
        TeacherEntity teacher = findTeacherById(id);
        if (teacher != null) {
            teacher.setDeleted(true);
            teacherRepository.save(teacher);
        }
    }

    @Override
    public void deleteTeacherPhysical(int id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public List<TeacherEntity> findTeachersByName(String name) {
        return teacherRepository.findByNameContainingIgnoreCaseAndIsDeletedFalse(name);
    }

    @Override
    public void deleteMultipleTeachers(List<Integer> ids) {
        List<TeacherEntity> teachers = teacherRepository.findAllById(ids);
        teachers.forEach(teacher -> teacher.setDeleted(true));
        teacherRepository.saveAll(teachers);
    }

    @Override
    public List<TeacherEntity> findTeachersByPage(int page, int size) {
        return teacherRepository.findAll(PageRequest.of(page - 1, size))
                .getContent()
                .stream()
                .filter(teacher -> !teacher.isDeleted())
                .toList();
    }
}
