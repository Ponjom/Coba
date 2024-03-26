package org.lily.dao;

import org.lily.entity.Course;

import java.util.List;

public interface CourseDao {
    List<Course> getAllCourses();
    Course getCourse(Long id);
}
