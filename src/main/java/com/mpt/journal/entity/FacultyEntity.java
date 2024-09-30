package com.mpt.journal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "faculties")
public class FacultyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Название факультета не может быть пустым")
    private String name;

    // Связь OneToMany с курсами
    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    private List<CourseEntity> courses;

    // Связь OneToOne с кафедрой
    @OneToOne(mappedBy = "faculty", cascade = CascadeType.ALL)
    private DepartmentEntity department;

    @Column(nullable = false)
    private boolean isDeleted = false;

    public FacultyEntity() {}

    public FacultyEntity(String name) {
        this.name = name;
    }

    // Геттеры и сеттеры

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CourseEntity> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseEntity> courses) {
        this.courses = courses;
    }

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
