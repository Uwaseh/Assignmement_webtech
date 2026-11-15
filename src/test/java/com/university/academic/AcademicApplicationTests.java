package com.university.academic;

import com.university.academic.model.*;
import com.university.academic.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Saves a small set of data through every relationship and reads it back.
 * Runs against the real university_academic_db, and rolls back afterwards
 * so the database stays clean.
 */
@SpringBootTest
@Transactional
class AcademicApplicationTests {

    @Autowired DepartmentRepository departmentRepository;
    @Autowired LecturerRepository lecturerRepository;
    @Autowired CourseRepository courseRepository;
    @Autowired StudentRepository studentRepository;
    @Autowired StudentProfileRepository studentProfileRepository;
    @Autowired RegistrationRepository registrationRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void entityMappingsWork() {
        // department + its head lecturer
        Department cs = departmentRepository.save(new Department("CSX", "Computer Science"));

        Lecturer alice = lecturerRepository.save(new Lecturer("Alice", "Uwase", "alice.test@uni.ac.rw", cs));
        Lecturer bob = lecturerRepository.save(new Lecturer("Bob", "Mugisha", "bob.test@uni.ac.rw", cs));

        cs.setHead(alice);
        departmentRepository.save(cs);

        // a course taught by two lecturers (join table)
        Course web = new Course("WEBX301", "Web Technology and Internet", 4, cs);
        web.addLecturer(alice);
        web.addLecturer(bob);
        courseRepository.save(web);

        // a student with a profile (one-to-one)
        Student student = new Student("REGX2026001", "Eric", "Niyonzima",
                "eric.test@uni.ac.rw", LocalDate.of(2026, 9, 1), cs);
        student.setProfile(new StudentProfile(LocalDate.of(2004, 3, 15), Gender.MALE,
                "+250788000000", "Kigali, Gasabo"));
        studentRepository.save(student);

        // a registration (join entity with its own data)
        Registration reg = new Registration(student, web, LocalDate.now(), "2026/2027", Semester.SEMESTER_ONE);
        reg.setStatus(RegistrationStatus.APPROVED);
        reg.setGrade("A");
        registrationRepository.save(reg);

        // ---- check everything came back as expected ----
        assertEquals("Alice", departmentRepository.findByCode("CSX").orElseThrow().getHead().getFirstName());
        assertEquals(2, courseRepository.findByCode("WEBX301").orElseThrow().getLecturers().size());
        assertEquals(1, courseRepository.findByLecturersId(bob.getId()).size());
        assertNotNull(studentRepository.findByRegistrationNumber("REGX2026001").orElseThrow().getProfile().getId());

        Registration saved = registrationRepository.findByStudentId(student.getId()).get(0);
        assertEquals("WEBX301", saved.getCourse().getCode());
        assertEquals(RegistrationStatus.APPROVED, saved.getStatus());
        assertEquals("A", saved.getGrade());
    }
}
