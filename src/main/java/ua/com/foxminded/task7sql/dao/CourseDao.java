package ua.com.foxminded.task7sql.dao;

import ua.com.foxminded.task7sql.domain.Course;

import java.util.List;

public interface CourseDao extends CrudDao<Course, Integer>{
    void clearAndSetDataToCourseTable(List<Course> courses);
}
