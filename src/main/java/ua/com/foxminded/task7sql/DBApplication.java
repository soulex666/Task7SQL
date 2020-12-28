package ua.com.foxminded.task7sql;

import ua.com.foxminded.task7sql.jdbcconnector.DBConnectorJDBC;
import ua.com.foxminded.task7sql.domain.Course;
import ua.com.foxminded.task7sql.domain.Group;
import ua.com.foxminded.task7sql.domain.Student;
import ua.com.foxminded.task7sql.generator.CourseCreator;
import ua.com.foxminded.task7sql.generator.CourseCreatorImpl;
import ua.com.foxminded.task7sql.generator.GroupGeneratorImpl;
import ua.com.foxminded.task7sql.generator.StudentsGeneratorImpl;
import ua.com.foxminded.task7sql.provider.DatabaseProvider;

import java.sql.*;
import java.util.*;

public class DBApplication {

    public static void main(String[] args) {
        StudentsGeneratorImpl studentsGenerator = new StudentsGeneratorImpl();
        GroupGeneratorImpl groupGenerator = new GroupGeneratorImpl();
        CourseCreator courseCreator = new CourseCreatorImpl();
        DatabaseProvider provider = new DatabaseProvider();

        List<Group> groups = groupGenerator.generateGroups();
        List<Student> students = studentsGenerator.generateStudents();
        List<Course> courses = courseCreator.getCourses();

        DBConnectorJDBC connector = null;
        try {
            connector = new DBConnectorJDBC();

            /* set all random data to tables
            provider.clearAndSetDataToGroupTable(connector, groups);
            provider.clearAndSetDataToCourseTable(connector, courses);
            provider.clearAndSetDataToStudentsTable(connector, students);
            provider.clearAndSetRandomDataToStudentsEnrollmentTable(connector); */

            //get groups with minimum students to console
            //connector.getGroupsWithMinimumStudents();

            //deleted student by ID
            //connector.deleteStudentById(5);

            //set student by ID to course by ID
            //connector.setCourseEnrollmentData(studentId, courseId);

            //delete student by ID from course by ID
            //connector.deleteStudentFromCourseByCourseId(1, 5);

            //get table data by table name to ResultSet
            //ResultSet groupsData = connector.getTableDataByName("groups");

            //create many to many table between students and courses if needed
            //connector.getGroupWithLessStudents();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(connector).disconnect();
        }
    }
}
