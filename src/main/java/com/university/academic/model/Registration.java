package com.university.academic.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Join entity between Student and Course.
 * A plain join table is not enough here because every registration
 * carries its own data: date, academic year, semester, status and grade.
 */
@Entity
@Table(
        name = "registrations",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"student_id", "course_id", "academic_year", "semester"})
)
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "academic_year", nullable = false, length = 9)
    private String academicYear;      // e.g. "2026/2027"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private Semester semester;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private RegistrationStatus status = RegistrationStatus.PENDING;

    @Column(length = 2)
    private String grade;             // e.g. "A", "B+"; empty until results are out

    public Registration() {
    }

    public Registration(Student student, Course course, LocalDate registrationDate,
                        String academicYear, Semester semester) {
        this.student = student;
        this.course = course;
        this.registrationDate = registrationDate;
        this.academicYear = academicYear;
        this.semester = semester;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }

    public Semester getSemester() { return semester; }
    public void setSemester(Semester semester) { this.semester = semester; }

    public RegistrationStatus getStatus() { return status; }
    public void setStatus(RegistrationStatus status) { this.status = status; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}
