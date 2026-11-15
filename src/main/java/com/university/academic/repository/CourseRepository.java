package com.university.academic.repository;

import com.university.academic.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByCode(String code);

    List<Course> findByDepartmentId(Long departmentId);

    // courses taught by a given lecturer (goes through the course_lecturers join table)
    List<Course> findByLecturersId(Long lecturerId);
}
