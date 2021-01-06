package ua.com.foxminded.task7sql.dao;

import ua.com.foxminded.task7sql.domain.Student;

import java.util.List;

public interface StudentsDao extends CrudDao<Student, Integer> {
    List<Student> getStudentsFromCourseByCourseName(String courseName);
}
