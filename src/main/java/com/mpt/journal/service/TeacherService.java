package com.mpt.journal.service;

import com.mpt.journal.entity.TeacherEntity;
import java.util.List;

public interface TeacherService {
    List<TeacherEntity> findAllTeachers();
    TeacherEntity findTeacherById(int id);
    TeacherEntity addTeacher(TeacherEntity teacher);
    TeacherEntity updateTeacher(TeacherEntity teacher);
    void deleteTeacher(int id); // Логическое удаление
    void deleteTeacherPhysical(int id); // Физическое удаление
    List<TeacherEntity> findTeachersByName(String name);
    void deleteMultipleTeachers(List<Integer> ids);
    List<TeacherEntity> findTeachersByPage(int page, int size);
}
