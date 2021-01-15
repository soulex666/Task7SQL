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
    private static final String NEWLINE_DELIMITER = "\n";
    private static final String OPERATION_NAME = "Введите название операции, которую хотите произвести:";
    private static final String LIST_REQUEST_FOR_STUDENTS = "get, set, update, delete by id, get by course name, set group id";
    private static final String LIST_REQUEST_FOR_COURSES = "get, set, update, delete by id";
    private static final String LIST_REQUEST_FOR_GROUPS = "get, set, get min, update, delete by id";
    private static final String ERROR_REQUEST = "Данного запроса не существует";

    public void studentsWriter(StudentsDao studentsDao, View<Student> studentView) {
        System.out.println("Вы выбрали таблицу Students");
        System.out.println(OPERATION_NAME);
        System.out.println(LIST_REQUEST_FOR_STUDENTS);
        String temp2 = ConsoleReader.readLine();

        switch (temp2) {
            case "get":
                System.out.println("Введите id студента");
                int studentIdGet = ConsoleReader.readInt();
                Optional<Student> student = studentsDao.getById(studentIdGet);
                if (student.isPresent()) {
                    studentView.consoleView(Collections.singletonList(student.get()));
                } else {
                    System.out.printf("Студента с ID = %d не найдено%s", studentIdGet, NEWLINE_DELIMITER);
                }
                break;
            case "set":
                System.out.println("Введите данные студента, которого хотите добавить в базу");
                System.out.println("Введите имя студента:");
                String firstNameSet = ConsoleReader.readLine();
                System.out.println("Введите фамилию студента:");
                String lastNameSet = ConsoleReader.readLine();
                Student studentSet = Student.builder()
                        .withFirstName(firstNameSet)
                        .withLastName(lastNameSet)
                        .build();
                studentsDao.set(studentSet);

                System.out.printf("Студент %s %s добавлен в базу%s", firstNameSet, lastNameSet, NEWLINE_DELIMITER);
                break;
            case "update" :
                System.out.println("Введите данные студента, которые хотите обновить в базе");
                System.out.println("Введите ID студента");
                int studentIdUpd = ConsoleReader.readInt();
                System.out.println("Введите имя студента:");
                String firstNameUpd = ConsoleReader.readLine();
                System.out.println("Введите фамилию студента:");
                String lastNameUpd = ConsoleReader.readLine();
                System.out.println("Введите группу студента:");
                int groupIdUpd = ConsoleReader.readInt();
                Student studentUpd = Student.builder()
                        .withStudentId(studentIdUpd)
                        .withFirstName(firstNameUpd)
                        .withLastName(lastNameUpd)
                        .withGroupId(groupIdUpd)
                        .build();
                studentsDao.update(studentUpd);
                System.out.printf("Данные студента с ID = %d обновлены%s", studentIdUpd, NEWLINE_DELIMITER);
                break;
            case "delete by id" :
                System.out.println("Введите ID студента, которого хотитет удалить из базы");
                int studentIdDel = ConsoleReader.readInt();
                studentsDao.deleteById(studentIdDel);
                System.out.printf("Студент с ID = %d удалён%s", studentIdDel, NEWLINE_DELIMITER);
                break;
            case "get by course name" :
                System.out.println("Введите название курса");
                String courseName = ConsoleReader.readLine();
                studentView.consoleView(studentsDao.getStudentsFromCourseByCourseName(courseName));
                break;
            case "set group id" :
                System.out.println("Введите номер студента");
                int studentId = ConsoleReader.readInt();
                System.out.println("Введите номер группы");
                int groupId = ConsoleReader.readInt();
                studentsDao.setGroupIdToStudent(studentId, groupId);
                System.out.printf("Студент с номером %d добавлен в группу %d%s", studentId, groupId, NEWLINE_DELIMITER);
                break;
            default:
                System.out.println(ERROR_REQUEST);
        }
    }

    public void coursesWriter(CourseDao courseDao, View<Course> courseView) {
        System.out.println("Вы выбрали таблицу courses");
        System.out.println(OPERATION_NAME);
        System.out.println(LIST_REQUEST_FOR_COURSES);
        String temp2 = ConsoleReader.readLine();
        switch (temp2) {
            case "get":
                System.out.println("Введите id курса");
                int id = ConsoleReader.readInt();
                Optional<Course> course = courseDao.getById(id);
                System.out.println(course.isPresent());
                if (course.isPresent()) {
                    courseView.consoleView(Collections.singletonList(course.get()));
                } else {
                    System.out.printf("Курс с ID = %d не найдено%s", id, NEWLINE_DELIMITER);
                }
                break;
            case "set":
                System.out.println("Введите название курса");
                String courseName = ConsoleReader.readLine();
                System.out.println("Введите описание курса");
                String courseDescription = ConsoleReader.readLine();
                Course newCourse = Course.builder()
                        .withCourseName(courseName)
                        .withCourseDescription(courseDescription)
                        .build();
                courseDao.set(newCourse);
                System.out.printf("Курс '%s' записан в базу данных%s", courseName, NEWLINE_DELIMITER);
                break;
            case "update" :
                System.out.println("Введите данные курса, которые хотите обновить в базе");
                System.out.println("Введите ID курса");
                int courseIdUpd = ConsoleReader.readInt();
                System.out.println("Введите название курса курса");
                String courseNameUpd = ConsoleReader.readLine();
                System.out.println("Введите описание курса курса");
                String courseDescriptionUpd = ConsoleReader.readLine();
                Course courseUpd = Course.builder()
                        .withCourseId(courseIdUpd)
                        .withCourseName(courseNameUpd)
                        .withCourseDescription(courseDescriptionUpd)
                        .build();
                courseDao.update(courseUpd);
                System.out.printf("Курс с ID = %d обновлён в базе данных%s", courseIdUpd, NEWLINE_DELIMITER);
                break;
            case "delete by id" :
                System.out.println("Введите ID курса, который хотитет удалить из базы");
                int courseIdDel = ConsoleReader.readInt();
                courseDao.deleteById(courseIdDel);
                System.out.printf("Курс с ID = %d удалён%s", courseIdDel, NEWLINE_DELIMITER);
                break;
            default:
                System.out.println(ERROR_REQUEST);
        }
    }

    public void groupsWriter(GroupDao groupDao, View<Group> groupView) {
        System.out.println("Вы выбрали таблицу groups");
        System.out.println(OPERATION_NAME);
        System.out.println(LIST_REQUEST_FOR_GROUPS);
        String temp2 = ConsoleReader.readLine();
        switch (temp2) {
            case "get":
                System.out.println("Введите id группы");
                int id = ConsoleReader.readInt();
                Optional<Group> group = groupDao.getById(id);
                if (group.isPresent()) {
                    groupView.consoleView(Collections.singletonList(group.get()));
                } else {
                    System.out.printf("Группы с ID = %d не найдено%s", id, NEWLINE_DELIMITER);
                }
                break;
            case "set":
                System.out.println("Введите название группы, не более 5 символов");
                String groupName = ConsoleReader.readLine();
               Group newGroup = Group.builder()
                       .withGroupName(groupName)
                       .build();
                groupDao.set(newGroup);
                System.out.printf("Группа с названием '%s' записана в базу данных%s", groupName, NEWLINE_DELIMITER);
                break;
            case "update" :
                System.out.println("Введите данные группы, которые хотите обновить в базе");
                System.out.println("Введите ID группы");
                int groupIdUpd = ConsoleReader.readInt();
                System.out.println("Введите название группы, не более 5 символов");
                String groupNameUpd = ConsoleReader.readLine();
                Group groupUpd = Group.builder()
                        .withGroupId(groupIdUpd)
                        .withGroupName(groupNameUpd)
                        .build();
                groupDao.update(groupUpd);
                System.out.printf("Группа с ID = %d записана в базу данных%s", groupIdUpd, NEWLINE_DELIMITER);
                break;
            case "delete by id" :
                System.out.println("Введите ID группы, которую хотитет удалить из базы");
                int groupIdDel = ConsoleReader.readInt();
                groupDao.deleteById(groupIdDel);
                System.out.printf("Группа с ID = %d удалена%s", groupIdDel, NEWLINE_DELIMITER);
                break;
            case "get min" :
                System.out.println("Группы с минимальным количеством студентов:");
                groupView.consoleView(groupDao.getGroupsWithMinimumStudents());
                break;
            default:
                System.out.println(ERROR_REQUEST);
        }
    }
}
