package ua.com.foxminded.task7sql.dbconnectorjdbc;

import ua.com.foxminded.task7sql.validator.DBRuntimeException;

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
    private static final String UNABLE_TO_GET_DATA = "Unable to get table data";
    private static final String SET_DATA_PROBLEM = "Unable to set data";
    private static final String CLEAR_TABLE_PROBLEM = "Unable to clear data";
    private static final String CREATE_TABLE_PROBLEM = "Unable to create table";
    private static final String DELETE_STUDENT_PROBLEM = "Unable to delete student";
    private static final String PROBLEM_TO_CLOSE_CONNECTIONS = "Unable to get table data";
    private static final String UNABLE_TO_CONNECT_DATABASE = "Unable to connect database";
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
            "WHERE course_name = '%s')) " +
            "ORDER BY student_id ASC;";
    private static final String GET_GROUPS_WITH_MINIMUM_STUDENTS =
            "SELECT group_id, MIN(t.group_count) AS min " +
                    "FROM (SELECT group_id, COUNT(group_id) AS group_count " +
                    "FROM students " +
                    "WHERE group_id IS NOT NULL " +
                    "GROUP BY group_id) AS t " +
                    "WHERE t.group_count = (" +
                    "SELECT MIN(tt.group_count) AS min " +
                    "FROM (SELECT group_id, COUNT(group_id) AS group_count " +
                    "FROM students " +
                    "WHERE group_id IS NOT NULL " +
                    "GROUP BY group_id) AS tt) " +
                    "GROUP BY group_id";
    private static final String GET_TABLE_DATA_BY_NAME = "SELECT  * FROM %s;";


    public DBConnectorJDBC() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new DBRuntimeException(UNABLE_TO_CONNECT_DATABASE, e);
        }
    }

    public void deleteStudentById(int studentId) {
        try {
            statement.executeUpdate(String.format(DELETE_STUDENT_BY_ID, studentId));
        } catch (SQLException e) {
            throw new DBRuntimeException(DELETE_STUDENT_PROBLEM, e);
        }
    }

    public void deleteStudentFromCourseByCourseId(int studentId, int courseId) {
        try {
            statement.executeUpdate(String.format(DELETE_STUDENT_BY_COURSE_ID, studentId, courseId));
        } catch (SQLException e) {
            throw new DBRuntimeException(DELETE_STUDENT_PROBLEM, e);
        }
    }

    public void setGroupData(String groupName) {
        try {
            statement.executeUpdate(String.format(SET_GROUP_DATA, groupName));
        } catch (SQLException e) {
            throw new DBRuntimeException(SET_DATA_PROBLEM, e);
        }
    }

    public void createManyToManyTable() {
        try {
            statement.executeUpdate(CREATE_COURSE_ENROLLMENT_TABLE);
        } catch (SQLException e) {
            throw new DBRuntimeException(CREATE_TABLE_PROBLEM, e);
        }
    }

    public void setCourseEnrollmentData(int studentId, int courseId) {
        try {
            statement.executeUpdate(String.format(SET_COURSE_ENROLLMENT_DATA, studentId, courseId));
        } catch (SQLException e) {
            throw new DBRuntimeException(SET_DATA_PROBLEM, e);
        }
    }


    public void setStudentDataWithGroupId(String firstName, String lastName, int groupId) {
        try {
            statement.executeUpdate(String.format(SET_STUDENT_DATA_WITH_GROUP_ID, groupId, firstName, lastName));
        } catch (SQLException e) {
            throw new DBRuntimeException(SET_DATA_PROBLEM, e);
        }
    }

    public void setStudentDataWithoutGroupId(String firstName, String lastName) {
        try {
            statement.executeUpdate(String.format(SET_STUDENT_DATA_WITHOUT_GROUP_ID, firstName, lastName));
        } catch (SQLException e) {
            throw new DBRuntimeException(SET_DATA_PROBLEM, e);
        }
    }

    public void setCourseData(String courseName, String courseDescription) {
        try {
            statement.executeUpdate(String.format(SET_COURSE_DATA, courseName, courseDescription));
        } catch (SQLException e) {
            throw new DBRuntimeException(SET_DATA_PROBLEM, e);
        }
    }

    public void clearTableByName(String tableName) {
        try {
            statement.executeUpdate(String.format(CLEAR_TABLE_BY_NAME, tableName));
        } catch (SQLException e) {
            throw new DBRuntimeException(CLEAR_TABLE_PROBLEM, e);
        }
    }

    public ResultSet getTableDataByName(String tableName) {
        try {
            return statement.executeQuery(String.format(GET_TABLE_DATA_BY_NAME, tableName));
        } catch (SQLException e) {
            throw new DBRuntimeException(UNABLE_TO_GET_DATA, e);
        }
    }

    public ResultSet getStudentsFromCourseByCourseName(String courseName) {
        try {
            return statement.executeQuery(String.format(GET_STUDENTS_FROM_COURSE, courseName));
        } catch (SQLException e) {
            throw new DBRuntimeException(UNABLE_TO_GET_DATA, e);
        }
    }

    public ResultSet getGroupsWithMinimumStudents() {
        try {
            return statement.executeQuery(GET_GROUPS_WITH_MINIMUM_STUDENTS);
        } catch (SQLException e) {
            throw new DBRuntimeException(UNABLE_TO_GET_DATA, e);
        }
    }

    public void disconnect() {
        try {
            statement.close();
        } catch (SQLException e) {
            throw new DBRuntimeException(PROBLEM_TO_CLOSE_CONNECTIONS, e);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DBRuntimeException(PROBLEM_TO_CLOSE_CONNECTIONS, e);
        }
    }
}
