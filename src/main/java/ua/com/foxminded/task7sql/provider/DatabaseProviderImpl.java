package ua.com.foxminded.task7sql.provider;

import ua.com.foxminded.task7sql.domain.Course;
import ua.com.foxminded.task7sql.domain.Group;
import ua.com.foxminded.task7sql.domain.Student;
import ua.com.foxminded.task7sql.dbconnectorjdbc.DBConnectorJDBC;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class DatabaseProviderImpl implements DatabaseProvider {
    private static final Random RANDOM = new Random();
    private static final int NUMBER_ZERO = 0;
    private static final int NUMBER_ONE = 1;
    private static final int NUMBER_THREE = 3;
    private static final int NUMBER_TEN = 10;
    private static final int NUMBER_TWO_HUNDRED = 200;
    private static final String COURSES = "courses";
    private static final String STUDENTS = "students";
    private static final String GROUPS = "groups";
    private static final String COURSE_ENROLLMENT = "course_enrollment";

    public void clearAndSetDataToCourseTable(DBConnectorJDBC connector, List<Course> courses) {
        connector.clearTableByName(COURSES);
        for (Course course : courses) {
            connector.setCourseData(course.getCourseName(), course.getCourseDescription());
        }
    }

    public void clearAndSetDataToStudentsTable(DBConnectorJDBC connector, List<Student> students) {
        connector.clearTableByName(STUDENTS);
        for (Student student : students) {
            if (student.getGroupId() == NUMBER_ZERO) {
                connector.setStudentDataWithoutGroupId(student.getFirstName(), student.getLastName());
            } else {
                connector.setStudentDataWithGroupId(student.getFirstName(),
                        student.getLastName(), student.getGroupId());
            }
        }
    }

    public void clearAndSetDataToGroupTable(DBConnectorJDBC connector, List<Group> groups) {
        connector.clearTableByName(GROUPS);
        for (Group group : groups) {
            connector.setGroupData(group.getGroupName());
        }
    }

    public void clearAndSetRandomDataToStudentsEnrollmentTable(DBConnectorJDBC connector) {

    }
}
