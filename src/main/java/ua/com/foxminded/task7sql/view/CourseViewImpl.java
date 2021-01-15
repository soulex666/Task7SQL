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
            System.out.printf("Course ID: %d%s", course.getCourseId(), NEWLINE_DELIMITER);
            System.out.printf("Course Name: %s%s", course.getCourseName(), NEWLINE_DELIMITER);
            System.out.printf("Course description: %s%s", course.getCourseDescription(), NEWLINE_DELIMITER);
            System.out.println("------------------------------------------------------");
        }
    }
}
