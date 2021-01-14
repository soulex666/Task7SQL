package ua.com.foxminded.task7sql.view;


import ua.com.foxminded.task7sql.domain.Course;

import java.util.List;

public class CourseViewImpl implements View<Course>{
    @Override
    public void consoleView(List<Course> entity) {
        if(entity.isEmpty()) {
            System.out.println("Совпадений не найдено");
            return;
        }
        for(Course course : entity) {
            System.out.println("Course ID: " + course.getCourseId());
            System.out.println("Course Name: " + course.getCourseName());
            System.out.println("Course description: " + course.getCourseDescription());
            System.out.println("------------------------------------------------------");
        }

    }
}
