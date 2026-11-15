package com.university.academic.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A department offers courses, employs lecturers and admits students.
 * One of its lecturers is the head of the department.
 */
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String code;          // e.g. "CS"

    @Column(nullable = false, length = 100)
    private String name;          // e.g. "Computer Science"

    // One department has one head, and a lecturer can head only one department
    @OneToOne
    @JoinColumn(name = "head_lecturer_id", unique = true)
    private Lecturer head;

    @OneToMany(mappedBy = "department")
    private List<Lecturer> lecturers = new ArrayList<>();

    @OneToMany(mappedBy = "department")
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "department")
    private List<Student> students = new ArrayList<>();

    public Department() {
    }

    public Department(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Lecturer getHead() { return head; }
    public void setHead(Lecturer head) { this.head = head; }

    public List<Lecturer> getLecturers() { return lecturers; }
    public void setLecturers(List<Lecturer> lecturers) { this.lecturers = lecturers; }

    public List<Course> getCourses() { return courses; }
    public void setCourses(List<Course> courses) { this.courses = courses; }

    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
}
