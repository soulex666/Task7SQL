package ua.com.foxminded.task7sql.jdbcconnector;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectorJDBC {
    private final Connection connection;
    private final Statement statement;
    private static final String USER = "admin";
    private static final String PASSWORD = "364821";
    private static final String URL = "jdbc:postgresql://localhost:5433/school_db";
    private static final String DELETE_STUDENT_BY_ID = "DELETE FROM students WHERE student_id='%d';";
    private static final String DELETE_STUDENT_BY_COURSE_ID =
            "DELETE FROM course_enrollment " +
                    "WHERE student_id='%d' " +
                    "AND course_id='%d';";
    private static final String SET_GROUP_DATA = "INSERT INTO groups (group_name) VALUES ('%s');";
    private static final String CREATE_COURSE_ENROLLMENT_TABLE =
            "CREATE TABLE course_enrollment(" +
                    "student_id INT," +
                    "course_id INT," +
                    "FOREIGN KEY (student_id) REFERENCES students(student_id)," +
                    "FOREIGN KEY (course_id) REFERENCES courses(course_id)," +
                    "UNIQUE (student_id, course_id));";

    private static final String SET_COURSE_ENROLLMENT_DATA =
            "INSERT INTO course_enrollment (student_id, course_id) " +
                    "VALUES ('%d', '%d');";
    private static final String CLEAR_TABLE_BY_NAME = "TRUNCATE TABLE %s RESTART IDENTITY CASCADE;";
    private static final String SET_STUDENT_DATA_WITH_GROUP_ID =
            "INSERT INTO students (group_id, first_name, last_name) " +
                    "VALUES ('%d', '%s', '%s');";
    private static final String SET_STUDENT_DATA_WITHOUT_GROUP_ID = "INSERT INTO students (first_name, last_name) " +
            "VALUES ('%s', '%s');";
    private static final String SET_COURSE_DATA = "INSERT INTO courses (course_name, course_description) " +
            "VALUES ('%s', '%s');";
    private static final String GET_STUDENTS_FROM_COURSE = "SELECT * " +
            "FROM students " +
            "WHERE student_id = ANY (" +
            "SELECT student_id " +
            "FROM course_enrollment " +
            "WHERE course_id = ANY (" +
            "SELECT course_id " +
            "FROM courses " +
            "WHERE course_name = '%s'));";
    private static final String GET_GROUPS_WITH_MINIMUM_STUDENTS =
            "SELECT DISTINCT group_id, MIN(t.group_count) AS min " +
                    "FROM (SELECT group_id, COUNT(group_id) AS group_count " +
                    "FROM students " +
                    "WHERE group_id IS NOT NULL " +
                    "GROUP BY group_id) AS t " +
                    "WHERE t.group_count = (" +
                    "SELECT DISTINCT MIN(t.group_count) AS min " +
                    "FROM (SELECT group_id, COUNT(group_id) AS group_count " +
                    "FROM students " +
                    "WHERE group_id IS NOT NULL " +
                    "GROUP BY group_id) AS t) " +
                    "GROUP BY group_id";
    private static final String GET_TABLE_DATA_BY_NAME = "SELECT  * FROM %s;";


    public DBConnectorJDBC() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new SQLException("Unable to connect");
        }
    }

    public void deleteStudentById(int studentId) throws SQLException {
        statement.executeUpdate(String.format(DELETE_STUDENT_BY_ID, studentId));
    }

    public void deleteStudentFromCourseByCourseId(int studentId, int courseId) throws SQLException {
        statement.executeUpdate(String.format(DELETE_STUDENT_BY_COURSE_ID, studentId, courseId));
    }

    public void setGroupData(String groupName) throws SQLException {
        statement.executeUpdate(String.format(SET_GROUP_DATA, groupName));
    }

    public void createManyToManyTable() throws SQLException {
        statement.executeUpdate(CREATE_COURSE_ENROLLMENT_TABLE);
    }

    public void setCourseEnrollmentData(int studentId, int courseId) throws SQLException {
        statement.executeUpdate(String.format(SET_COURSE_ENROLLMENT_DATA, studentId, courseId));
    }


    public void setStudentDataWithGroupId(String firstName, String lastName, int groupId) throws SQLException {
        statement.executeUpdate(String.format(SET_STUDENT_DATA_WITH_GROUP_ID, groupId, firstName, lastName));
    }

    public void setStudentDataWithoutGroupId(String firstName, String lastName) throws SQLException {
        statement.executeUpdate(String.format(SET_STUDENT_DATA_WITHOUT_GROUP_ID, firstName, lastName));
    }

    public void setCourseData(String courseName, String courseDescription) throws SQLException {
        statement.executeUpdate(String.format(SET_COURSE_DATA, courseName, courseDescription));
    }

    public void clearTableByName(String tableName) throws SQLException {
        statement.executeUpdate(String.format(CLEAR_TABLE_BY_NAME, tableName));
    }

    public ResultSet getTableDataByName(String tableName) {
        try {
            return statement.executeQuery(String.format(GET_TABLE_DATA_BY_NAME, tableName));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public void getStudentsFromCourse(String courseName) {
        String request = String.format(GET_STUDENTS_FROM_COURSE, courseName);

        try (ResultSet rs = statement.executeQuery(request)) {
            while (rs.next()) {
                System.out.println(rs.getString("student_id") + "-" + rs.getString("first_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getGroupsWithMinimumStudents() {
        try (ResultSet rs = statement.executeQuery(GET_GROUPS_WITH_MINIMUM_STUDENTS)) {
            while (rs.next()) {
                System.out.println(rs.getInt("group_id") + " " + rs.getString("min"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
