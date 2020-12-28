package ua.com.foxminded.task7sql.provider;

import ua.com.foxminded.task7sql.domain.Course;
import ua.com.foxminded.task7sql.domain.Group;
import ua.com.foxminded.task7sql.domain.Student;
import ua.com.foxminded.task7sql.jdbcconnector.DBConnectorJDBC;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class DatabaseProvider {
    private static final Random RANDOM = new Random();
    private static final int NUMBER_ONE = 1;
    private static final int NUMBER_THREE = 3;
    private static final int NUMBER_TEN = 10;
    private static final int NUMBER_TWO_HUNDRED = 200;

    public boolean clearAndSetDataToCourseTable(DBConnectorJDBC connector, List<Course> courses) {
        try {
            connector.clearTableByName("courses");
            for (Course course : courses) {
                connector.setCourseData(course.getCourseName(), course.getCourseDescription());
            }

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean clearAndSetDataToStudentsTable(DBConnectorJDBC connector, List<Student> students) {
        try {
            connector.clearTableByName("students");
            for (Student student : students) {
                if(student.getGroupId() == 0) {
                    connector.setStudentDataWithoutGroupId(student.getFirstName(), student.getLastName());
                } else {
                    connector.setStudentDataWithGroupId(student.getFirstName(),
                            student.getLastName(), student.getGroupId());
                }
            }

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean clearAndSetDataToGroupTable(DBConnectorJDBC connector, List<Group> groups) {
        try {
            connector.clearTableByName("groups");
            for (Group group : groups) {
                connector.setGroupData(group.getGroupName());
            }

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean clearAndSetRandomDataToStudentsEnrollmentTable(DBConnectorJDBC connector) {
        try {
            connector.clearTableByName("course_enrollment");
            for (int i = 1; i <= NUMBER_TWO_HUNDRED; i++) {
                int numberOfCourses = RANDOM.nextInt(NUMBER_THREE) + NUMBER_ONE;
                final Set<Integer> tempSet = new HashSet<>(NUMBER_THREE);
                for (int j = 0; j < numberOfCourses; j++) {
                    int randomCourseId = RANDOM.nextInt(NUMBER_TEN) + NUMBER_ONE;
                    while (tempSet.contains(randomCourseId)) {
                        randomCourseId = RANDOM.nextInt(NUMBER_TEN) + NUMBER_ONE;
                    }
                    connector.setCourseEnrollmentData(i, randomCourseId);
                    tempSet.add(randomCourseId);
                }
            }

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
}
