package ua.com.foxminded.task7sql.view;


import ua.com.foxminded.task7sql.domain.Course;

public class CourseViewImpl implements View<Course>{
    @Override
    public void consoleView(Course entity) {
        System.out.println("Начало печати данных курса:");
        System.out.println("Course ID: " + entity.getCourseId());
        System.out.println("Course Name: " + entity.getCourseName());
        System.out.println("Course description: " + entity.getCourseDescription());
    }
}
