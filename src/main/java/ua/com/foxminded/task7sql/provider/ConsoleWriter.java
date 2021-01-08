package ua.com.foxminded.task7sql.provider;

import ua.com.foxminded.task7sql.dao.CourseDao;
import ua.com.foxminded.task7sql.dao.GroupDao;
import ua.com.foxminded.task7sql.dao.StudentsDao;
import ua.com.foxminded.task7sql.domain.Course;
import ua.com.foxminded.task7sql.domain.Group;
import ua.com.foxminded.task7sql.domain.Student;
import ua.com.foxminded.task7sql.view.View;

import java.util.Collections;
import java.util.Optional;

public class ConsoleWriter {
    private static final String OPERATION_NAME = "Введите название операции, которую хотите произвести:";

    public void studentsWriter(StudentsDao studentsDao, View<Student> studentView) {
        System.out.println("Вы выбрали таблицу Students");
        System.out.println(OPERATION_NAME);
        String temp2 = ConsoleReader.readLine();
        switch (temp2) {
            case "get":
                System.out.println("введите id студента");
                int id = ConsoleReader.readInt();
                Optional<Student> student = studentsDao.getById(id);
                System.out.println(id);
                if (student.isPresent()) {
                    studentView.consoleView(Collections.singletonList(student.get()));
                } else {
                    System.out.printf("Студента с ID = %d не найдено%s", id, "\n");
                }
                break;
            case "set":
                System.out.println("Введите данные студента, которого хотите добавить в базу");
                System.out.println("Введите имя:");
                String firstName = ConsoleReader.readLine();
                System.out.println("Введите фамилию:");
                String lastName = ConsoleReader.readLine();
                Student student1 = Student.builder()
                        .withFirstName(firstName)
                        .withLastName(lastName)
                        .build();
                System.out.println(student1.getFirstName() + " " + student1.getLastName());
                studentsDao.set(student1);

                System.out.printf("Студент %s %s добавлен в базу%s", firstName, lastName, "\n");
                break;
            case "get by course name" :
                System.out.println("Введите название курса");
                String courseName = ConsoleReader.readLine();

                studentView.consoleView(studentsDao.getStudentsFromCourseByCourseName(courseName));
                break;
            case "set course id" :
                System.out.println("Введите номер студента");
                int studentId = ConsoleReader.readInt();
                System.out.println("Введите номер курса");
                int courseId = ConsoleReader.readInt();
                studentsDao.setGroupIdToStudent(studentId, courseId);
                System.out.printf("Студент с номером %d добавлен в группу %d%s", studentId, courseId, "\n");
                break;
            default:
                System.out.println("ошибка ввода данных1");
        }
    }

    public void coursesWriter(CourseDao courseDao, View<Course> courseView) {
        System.out.println("Вы выбрали таблицу courses");
        System.out.println(OPERATION_NAME);
        String temp2 = ConsoleReader.readLine();
        switch (temp2) {
            case "get":
                System.out.println("введите id курса");
                int id = ConsoleReader.readInt();
                Optional<Course> course = courseDao.getById(id);
                if (course.isPresent()) {
                    courseView.consoleView(Collections.singletonList(course.get()));
                } else {
                    System.out.printf("Курс с ID = %d не найдено", id);
                }
                break;
            case "set":
                break;
            default:
                System.out.println("данного запроса не существует");
        }
    }

    public void groupsWriter(GroupDao groupDao, View<Group> groupView) {
        System.out.println("Вы выбрали таблицу groups");
        System.out.println(OPERATION_NAME);
        String temp2 = ConsoleReader.readLine();
        switch (temp2) {
            case "get":
                System.out.println("Введите id группы");
                int id = ConsoleReader.readInt();
                Optional<Group> group = groupDao.getById(id);
                if (group.isPresent()) {
                    groupView.consoleView(Collections.singletonList(group.get()));
                } else {
                    System.out.printf("Группы с ID = %d не найдено", id);
                }
                break;
            case "set":
                break;
            case "get min" :
                System.out.println("Группы с минимальным количеством студентов:");
                groupView.consoleView(groupDao.getGroupsWithMinimumStudents());
                break;
            default:
                System.out.println("данного запроса не существует");
        }
    }
}
