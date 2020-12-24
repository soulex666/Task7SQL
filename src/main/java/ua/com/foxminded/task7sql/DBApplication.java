package ua.com.foxminded.task7sql;

import ua.com.foxminded.task7sql.jdbcconnector.DBConnectorJDBC;
import ua.com.foxminded.task7sql.domain.Course;
import ua.com.foxminded.task7sql.domain.Group;
import ua.com.foxminded.task7sql.domain.Student;
import ua.com.foxminded.task7sql.generator.CourseCreator;
import ua.com.foxminded.task7sql.generator.CourseCreatorImpl;
import ua.com.foxminded.task7sql.generator.GroupGeneratorImpl;
import ua.com.foxminded.task7sql.generator.StudentsGeneratorImpl;

import java.sql.*;
import java.util.List;
import java.util.Random;

public class DBApplication {
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        StudentsGeneratorImpl studentsGenerator = new StudentsGeneratorImpl();
        GroupGeneratorImpl groupGenerator = new GroupGeneratorImpl();
        CourseCreator courseCreator = new CourseCreatorImpl();
        List<Group> groups = groupGenerator.generateGroups();
        List<Student> students = studentsGenerator.generateStudents();
        List<Course> courses = courseCreator.getCourses();

        DBConnectorJDBC connector = null;
        try {
            connector = new DBConnectorJDBC();

            //deleted student by ID
            //connector.deleteStudentById(5);

            //connect student by ID to course by ID
            //connector.insertManyToManyData(studentId, courseId);

            //delete student from only one course
            //connector.deleteStudentFromCourseByCourseId(1, 5);

            //get groups list to console
            //connector.getGroupData("groups");

            connector.getGroupWithLessStudents();


            /*connector.clearTable("groups");
            for (Group group : groups) {
                connector.insertGroupData(group.getGroupName());
            }
            connector.clearTable("students");
            for (Student student : students) {
                if(student.getGroup_id() == 0) {
                    connector.insertStudentDataWithoutGroupId(student.getFirstName(), student.getLastName());
                } else {
                    connector.insertStudentData(student.getFirstName(), student.getLastName(), student.getGroup_id());
                }
            }
            connector.clearTable("courses");
            for (Course course : courses) {
                connector.insertCourseData(course.getCourseName(), course.getCourseDescription());
            }
            connector.createManyToManyTable();

            for (int i = 1; i <= 200; i++) {
                int numberOfCourses = RANDOM.nextInt(3) + 1;
                Set<Integer> tempSet = new HashSet<>();
                for (int j = 0; j < numberOfCourses; j++) {
                    int randomCourseId = RANDOM.nextInt(10) + 1;
                    while (tempSet.contains(randomCourseId)) {
                        randomCourseId = RANDOM.nextInt(10) + 1;
                    }
                    connector.insertManyToManyData(i, randomCourseId);
                    tempSet.add(randomCourseId);
                }
            }*/

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connector.disconnect();
        }
    }


}
