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
            System.out.printf("Student ID: %d%s", student.getStudentId(), NEWLINE_DELIMITER);
            System.out.printf("First Name: %s%s", student.getFirstName(), NEWLINE_DELIMITER);
            System.out.printf("Last Name: %s%s", student.getLastName(), NEWLINE_DELIMITER);
            System.out.printf("Group ID: %s%s", (student.getGroupId() == 0 ?
                    "студент не состоит в группе" : String.valueOf(student.getGroupId())), NEWLINE_DELIMITER);
            System.out.println("------------------------------------------------------");
        }
    }
}
