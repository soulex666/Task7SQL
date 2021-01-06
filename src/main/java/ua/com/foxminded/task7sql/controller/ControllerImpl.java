package ua.com.foxminded.task7sql.controller;

import ua.com.foxminded.task7sql.*;
import ua.com.foxminded.task7sql.dao.*;
import ua.com.foxminded.task7sql.domain.Course;
import ua.com.foxminded.task7sql.domain.Group;
import ua.com.foxminded.task7sql.domain.Student;
import ua.com.foxminded.task7sql.view.CourseViewImpl;
import ua.com.foxminded.task7sql.view.GroupViewImpl;
import ua.com.foxminded.task7sql.view.StudentViewImpl;
import ua.com.foxminded.task7sql.view.View;

import java.util.Optional;
import java.util.Scanner;

public class ControllerImpl implements Controller {
    private static final String CONFIG_FILE = "config";
    private final DBConnector connector;
    private static final Scanner SCANNER = new Scanner(System.in);
    private View<Student> studentView;
    private View<Course> courseView;
    private View<Group> groupView;

    public ControllerImpl() {
        this.connector = new DBConnector(CONFIG_FILE);
    }

    public void run() {
        //подправить логику заполнения базы сгенерированными данными
        /*System.out.println("введите 'write', если хотите заполнить базу данных сгенерированными данными или нажмите enter");
        String generate = scanner.next();
        if (!generate.isEmpty() && "write".equals(generate)) {
            StudentsGenerator studentsGenerator = new StudentsGeneratorImpl();
            GroupGenerator groupGenerator = new GroupGeneratorImpl();
            CourseCreator courseCreator = new CourseCreatorImpl();
            DatabaseProvider provider = new DatabaseProviderImpl();

            List<Group> groups = groupGenerator.generateGroups();
            List<Student> students = studentsGenerator.generateStudents();
            List<Course> courses = courseCreator.getCourses();

            provider.clearAndSetDataToGroupTable(connector, groups);
            provider.clearAndSetDataToCourseTable(connector, courses);
            provider.clearAndSetDataToStudentsTable(connector, students);
            provider.clearAndSetRandomDataToStudentsEnrollmentTable(connector);
        }*/

        while (true) {
            System.out.println("Введите название таблицы, с которой хотите соединиться или 'exit' для завершения программы:");
            String temp = SCANNER.next();
            switch (temp) {
                case "students": {
                    System.out.println("Вы выбрали таблицу Students");
                    StudentsDao studentsDao = studentsDaoConnection();
                    System.out.println("Введите название операции, которую хотите произвести:");
                    String temp2 = SCANNER.next();
                    switch (temp2) {
                        case "get": {
                            System.out.println("введите id студента");
                            int id = SCANNER.nextInt();
                            Optional<Student> student = studentsDao.getById(id);
                            if (student.isPresent()) {
                                studentView.consoleView(student.get());
                            } else {
                                System.out.printf("Студента с ID = %d не найдено", id);
                            }
                            break;
                        }
                        case "set": {
                            break;
                        }
                        default:
                            System.out.println("ошибка ввода данных1");
                    }
                    break;
                }
                case "courses": {
                    System.out.println("Вы выбрали таблицу courses");
                    CourseDao courseDao = coursesDaoConnection();
                    System.out.println("Введите название операции, которую хотите произвести:");
                    String temp2 = SCANNER.next();
                    switch (temp2) {
                        case "get": {
                            System.out.println("введите id курса");
                            int id = SCANNER.nextInt();
                            Optional<Course> course = courseDao.getById(id);
                            if (course.isPresent()) {
                                courseView.consoleView(course.get());
                            } else {
                                System.out.printf("Курс с ID = %d не найдено", id);
                            }
                            break;
                        }
                        case "set" :
                            break;
                        default:
                            System.out.println("данного запроса не существует");
                    }
                    break;
                }
                case "groups": {
                    System.out.println("Вы выбрали таблицу groups");
                    GroupDao groupDao = groupsDaoConnection();
                    System.out.println("Введите название операции, которую хотите произвести:");
                    String temp2 = SCANNER.next();
                    switch (temp2) {
                        case "get": {
                            System.out.println("введите id группы");
                            int id = SCANNER.nextInt();
                            Optional<Group> group = groupDao.getById(id);
                            if (group.isPresent()) {
                                groupView.consoleView(group.get());
                            } else {
                                System.out.printf("Группы с ID = %d не найдено", id);
                            }
                            break;
                        }
                        case "set" :
                            break;
                        default:
                            System.out.println("данного запроса не существует");
                    }
                    break;
                }
                case "exit":
                    return;
                default:
                    System.out.println("таблицы с заданным названием не существует");
            }
        }
    }


    private StudentsDao studentsDaoConnection() {
        studentView = new StudentViewImpl();
        return new StudentsDaoImpl(connector);
    }

    private GroupDao groupsDaoConnection() {
        groupView = new GroupViewImpl();
        return new GroupDaoImpl(connector);
    }

    private CourseDao coursesDaoConnection() {
        courseView = new CourseViewImpl();
        return new CourseDaoImpl(connector);
    }
}
