package com.university.academic.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A course belongs to one department, is taught by one or more lecturers
 * and has many student registrations.
 */
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String code;          // e.g. "WEB301"

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false)
    private Integer credits;

    // Many courses are offered by one department
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    // Many-to-many with no extra data -> a plain join table, no extra entity needed
    @ManyToMany
    @JoinTable(
            name = "course_lecturers",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "lecturer_id")
    )
    private List<Lecturer> lecturers = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<Registration> registrations = new ArrayList<>();

    public Course() {
    }

    public Course(String code, String title, Integer credits, Department department) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.department = department;
    }

    // keeps both sides of the relationship in step
    public void addLecturer(Lecturer lecturer) {
        lecturers.add(lecturer);
        lecturer.getCourses().add(this);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public List<Lecturer> getLecturers() { return lecturers; }
    public void setLecturers(List<Lecturer> lecturers) { this.lecturers = lecturers; }

    public List<Registration> getRegistrations() { return registrations; }
    public void setRegistrations(List<Registration> registrations) { this.registrations = registrations; }
}
