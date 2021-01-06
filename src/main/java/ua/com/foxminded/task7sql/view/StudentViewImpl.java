package ua.com.foxminded.task7sql.view;

import ua.com.foxminded.task7sql.domain.Student;

public class StudentViewImpl implements View<Student> {
    @Override
    public void consoleView(Student entity) {
        System.out.println(entity.getStudentId() + ":" + entity.getFirstName());
    }
}
