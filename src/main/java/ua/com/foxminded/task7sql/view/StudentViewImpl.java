package ua.com.foxminded.task7sql.view;

import ua.com.foxminded.task7sql.domain.Student;

import java.util.List;

public class StudentViewImpl implements View<Student> {
    @Override
    public void consoleView(List<Student> entity) {
        if(entity.isEmpty()) {
            System.out.println("Совпадений не найдено");
            return;
        }
        for (Student student: entity) {
            System.out.println("Student ID: " + student.getStudentId());
            System.out.println("First Name: " + student.getFirstName());
            System.out.println("Last Name: " + student.getLastName());
            System.out.println("Group ID: " + (student.getGroupId() == 0 ?
                    "студент не состоит в группе" : student.getGroupId()));
            System.out.println("------------------------------------------------------");
        }

    }
}
