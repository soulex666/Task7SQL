package ua.com.foxminded.task7sql.controller;

import ua.com.foxminded.task7sql.connector.DBConnector;
import ua.com.foxminded.task7sql.dao.CourseDao;
import ua.com.foxminded.task7sql.dao.CourseDaoImpl;
import ua.com.foxminded.task7sql.dao.GroupDao;
import ua.com.foxminded.task7sql.dao.GroupDaoImpl;
import ua.com.foxminded.task7sql.dao.StudentsDao;
import ua.com.foxminded.task7sql.dao.StudentsDaoImpl;
import ua.com.foxminded.task7sql.domain.Course;
import ua.com.foxminded.task7sql.domain.Group;
import ua.com.foxminded.task7sql.domain.Student;

import ua.com.foxminded.task7sql.generator.CourseCreator;
import ua.com.foxminded.task7sql.generator.CourseCreatorImpl;
import ua.com.foxminded.task7sql.generator.CourseEnrollmentGenerator;
import ua.com.foxminded.task7sql.generator.CourseEnrollmentGeneratorImpl;
import ua.com.foxminded.task7sql.generator.GroupGenerator;
import ua.com.foxminded.task7sql.generator.GroupGeneratorImpl;
import ua.com.foxminded.task7sql.generator.StudentsGenerator;
import ua.com.foxminded.task7sql.generator.StudentsGeneratorImpl;
import ua.com.foxminded.task7sql.provider.ConsoleReader;
import ua.com.foxminded.task7sql.provider.ConsoleWriter;
import ua.com.foxminded.task7sql.view.CourseViewImpl;
import ua.com.foxminded.task7sql.view.GroupViewImpl;
import ua.com.foxminded.task7sql.view.StudentViewImpl;
import ua.com.foxminded.task7sql.view.View;

import java.util.List;
import java.util.Map;

public class ControllerImpl implements Controller {
    private static final String CONFIG_FILE = "config";
    private final View<Student> studentView = new StudentViewImpl();
    private final View<Course> courseView = new CourseViewImpl();
    private final View<Group> groupView = new GroupViewImpl();
    private final StudentsDao studentsDao;
    private final CourseDao courseDao;
    private final GroupDao groupDao;
    private static final ConsoleWriter CONSOLE_WRITER = new ConsoleWriter();


    public ControllerImpl() {
        DBConnector connector = new DBConnector(CONFIG_FILE);
        studentsDao = new StudentsDaoImpl(connector);
        groupDao = new GroupDaoImpl(connector);
        courseDao = new CourseDaoImpl(connector);
    }

    public void run() {
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
                    CONSOLE_WRITER.studentsWriter(studentsDao, studentView);
                    break;
                case "courses":
                    CONSOLE_WRITER.coursesWriter(courseDao, courseView);
                    break;
                case "groups":
                    CONSOLE_WRITER.groupsWriter(groupDao, groupView);
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("таблицы с заданным названием не существует");
            }
        }
    }



    private void setAllDataToDatabase() {
        StudentsGenerator studentsGenerator = new StudentsGeneratorImpl();
        GroupGenerator groupGenerator = new GroupGeneratorImpl();
        CourseCreator courseCreator = new CourseCreatorImpl();
        CourseEnrollmentGenerator courseEnrollmentGenerator = new CourseEnrollmentGeneratorImpl();

        List<Group> groups = groupGenerator.generateGroups();
        List<Student> students = studentsGenerator.generateStudents();
        List<Course> courses = courseCreator.getCourses();
        Map<Integer, List<Integer>> courseEnrollmentData = courseEnrollmentGenerator.getCourseEnrollmentData();

        courseDao.setAll(courses);
        groupDao.setAll(groups);
        studentsDao.setAll(students);
        for (int i = 1; i <= students.size(); i++) {
            if(students.get(i - 1).getGroupId() != 0) {
                studentsDao.setGroupIdToStudent(i, students.get(i - 1).getGroupId());
            }
        }

        studentsDao.clearCourseEnrollmentTable();
        for (int i = 1; i <= 200; i++) {
            for (int temp : courseEnrollmentData.get(i)) {
                studentsDao.setCourseEnrollmentData(i, temp);
            }
        }
    }
}
