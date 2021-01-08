package ua.com.foxminded.task7sql.controller;

import ua.com.foxminded.task7sql.*;
import ua.com.foxminded.task7sql.dao.*;
import ua.com.foxminded.task7sql.domain.Course;
import ua.com.foxminded.task7sql.domain.Group;
import ua.com.foxminded.task7sql.domain.Student;
import ua.com.foxminded.task7sql.generator.*;
import ua.com.foxminded.task7sql.provider.ConsoleReader;
import ua.com.foxminded.task7sql.provider.ConsoleWriter;
import ua.com.foxminded.task7sql.view.CourseViewImpl;
import ua.com.foxminded.task7sql.view.GroupViewImpl;
import ua.com.foxminded.task7sql.view.StudentViewImpl;
import ua.com.foxminded.task7sql.view.View;

import java.util.*;

public class ControllerImpl implements Controller {
    private static final String CONFIG_FILE = "config";
    private final DBConnector connector;
    private View<Student> studentView;
    private View<Course> courseView;
    private View<Group> groupView;
    private StudentsDao studentsDao;
    private CourseDao courseDao;
    private GroupDao groupDao;
    private static final ConsoleWriter CONSOLE_WRITER = new ConsoleWriter();

    public ControllerImpl() {
        this.connector = new DBConnector(CONFIG_FILE);
    }

    public void run() {
        //подправить логику заполнения базы сгенерированными данными
        System.out.println("введите 'write', если хотите заполнить базу данных сгенерированными данными или нажмите enter");
        String generate = ConsoleReader.readLine();
        if (!generate.isEmpty() && "write".equals(generate)) {
            setAllDataToDatabase();
        }

        while (true) {
            System.out.println("Введите название таблицы, с которой хотите соединиться или 'exit' для завершения программы:");
            String temp = ConsoleReader.readLine();
            switch (temp) {
                case "students":
                    studentsDao = studentsDaoConnection();
                    CONSOLE_WRITER.studentsWriter(studentsDao, studentView);
                    break;
                case "courses":
                    courseDao = new CourseDaoImpl(connector);
                    CONSOLE_WRITER.coursesWriter(courseDao, courseView);
                    break;
                case "groups":
                    groupDao = new GroupDaoImpl(connector);
                    CONSOLE_WRITER.groupsWriter(groupDao, groupView);
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("таблицы с заданным названием не существует");
            }
        }
    }


    private StudentsDao studentsDaoConnection() {
        if (studentView == null) {
            studentView = new StudentViewImpl();
        }
        if (studentsDao == null) {
            studentsDao = new StudentsDaoImpl(connector);
        }

        return studentsDao;
    }

    private GroupDao groupsDaoConnection() {
        if (groupView == null) {
            groupView = new GroupViewImpl();
        }
        if (groupDao == null) {
            groupDao = new GroupDaoImpl(connector);
        }

        return groupDao;
    }

    private CourseDao coursesDaoConnection() {
        if (courseView == null) {
            courseView = new CourseViewImpl();
        }
        if (courseDao == null) {
            courseDao = new CourseDaoImpl(connector);
        }

        return courseDao;
    }

    private void setAllDataToDatabase() {
        StudentsGenerator studentsGenerator = new StudentsGeneratorImpl();
        GroupGenerator groupGenerator = new GroupGeneratorImpl();
        CourseCreator courseCreator = new CourseCreatorImpl();

        List<Group> groups = groupGenerator.generateGroups();
        List<Student> students = studentsGenerator.generateStudents();
        List<Course> courses = courseCreator.getCourses();

        studentsDao = studentsDaoConnection();
        courseDao = coursesDaoConnection();
        groupDao = groupsDaoConnection();

        courseDao.setAll(courses);
        groupDao.setAll(groups);

        studentsDao.setAll(students);
        for (int i = 1; i <= students.size(); i++) {
            if(students.get(i - 1).getGroupId() != 0) {
                studentsDao.setGroupIdToStudent(i, students.get(i - 1).getGroupId());
            }
        }
    }
}
