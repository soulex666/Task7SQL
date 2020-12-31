package ua.com.foxminded.task7sql;

import ua.com.foxminded.task7sql.dbconnectorjdbc.DBConnectorJDBC;
import ua.com.foxminded.task7sql.domain.Course;
import ua.com.foxminded.task7sql.domain.Group;
import ua.com.foxminded.task7sql.domain.Student;
import ua.com.foxminded.task7sql.generator.StudentsGenerator;
import ua.com.foxminded.task7sql.generator.GroupGenerator;
import ua.com.foxminded.task7sql.generator.CourseCreator;
import ua.com.foxminded.task7sql.generator.StudentsGeneratorImpl;
import ua.com.foxminded.task7sql.generator.GroupGeneratorImpl;
import ua.com.foxminded.task7sql.generator.CourseCreatorImpl;
import ua.com.foxminded.task7sql.provider.DatabaseProvider;
import ua.com.foxminded.task7sql.provider.DatabaseProviderImpl;
import ua.com.foxminded.task7sql.validator.DBRuntimeException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class DBApplication {

    public static void main(String[] args) {
        StudentsGenerator studentsGenerator = new StudentsGeneratorImpl();
        GroupGenerator groupGenerator = new GroupGeneratorImpl();
        CourseCreator courseCreator = new CourseCreatorImpl();
        DatabaseProvider provider = new DatabaseProviderImpl();

        List<Group> groups = groupGenerator.generateGroups();
        List<Student> students = studentsGenerator.generateStudents();
        List<Course> courses = courseCreator.getCourses();

        DBConnectorJDBC connector = new DBConnectorJDBC();

        //set all random data to tables
        provider.clearAndSetDataToGroupTable(connector, groups);
        provider.clearAndSetDataToCourseTable(connector, courses);
        provider.clearAndSetDataToStudentsTable(connector, students);
        provider.clearAndSetRandomDataToStudentsEnrollmentTable(connector);

        //get groups with minimum students
        ResultSet min = connector.getGroupsWithMinimumStudents();
        try {
            while (min.next()) {
                System.out.println("Group number: " + min.getInt("group_id") +
                        " - Number of students: " + min.getString("min"));

            }
        } catch (SQLException e) {
            throw new DBRuntimeException(e);
        }

        //get students from course by course name
        ResultSet groupsData = connector.getStudentsFromCourseByCourseName("math");
        try {
            while (groupsData.next()) {
                System.out.println(groupsData.getString("student_id") + "-" +
                        groupsData.getString("first_name") + " " +
                        groupsData.getString("last_name"));
            }
        } catch (SQLException e) {
            throw new DBRuntimeException(e);
        }


        //deleted student by ID
        //connector.deleteStudentById(1);

        //set student by ID to course by ID
        //connector.setCourseEnrollmentData(studentId, courseId);

        //delete student by ID from course by ID
        //connector.deleteStudentFromCourseByCourseId(studentId, courseId);

        //create many to many table between students and courses if needed
        //connector.createManyToManyTable();

        Objects.requireNonNull(connector).disconnect();
    }
}
