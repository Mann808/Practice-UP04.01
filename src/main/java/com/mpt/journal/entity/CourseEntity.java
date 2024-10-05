package com.mpt.journal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "courses")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Название курса не может быть пустым")
    private String courseName;

    @NotBlank(message = "Описание курса не может быть пустым")
    private String courseDescription;

    // Связь с факультетом (ManyToOne)
    @ManyToOne
    @JoinColumn(name = "faculty_id", referencedColumnName = "id")
    private FacultyEntity faculty;

    // Связь с преподавателями (ManyToMany)
    @ManyToMany(mappedBy = "courses")
    private List<TeacherEntity> teachers;

    @Column(nullable = false)
    private boolean isDeleted = false;

    public CourseEntity() {}

    public CourseEntity(String courseName, String courseDescription, FacultyEntity faculty) {
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.faculty = faculty;
    }

    // Геттеры и сеттеры

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public FacultyEntity getFaculty() {
        return faculty;
    }

    public void setFaculty(FacultyEntity faculty) {
        this.faculty = faculty;
    }

    public List<TeacherEntity> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherEntity> teachers) {
        this.teachers = teachers;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
