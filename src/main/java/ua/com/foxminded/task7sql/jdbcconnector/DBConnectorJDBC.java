package ua.com.foxminded.task7sql.jdbcconnector;

import java.sql.*;

public class DBConnectorJDBC {
    private final Connection connection;
    private final Statement statement;
    private static final String USER = "admin";
    private static final String PASSWORD = "364821";
    private static final String URL = "jdbc:postgresql://localhost:5433/school_db";

    public DBConnectorJDBC() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw  new SQLException("Unable to connect");
        }
    }

    public void deleteStudentById(int studentId) throws SQLException {
        statement.executeUpdate(String.format("DELETE FROM students WHERE student_id='%d';", studentId));
    }

    public void deleteStudentFromCourseByCourseId(int studentId, int courseId) throws SQLException {
        statement.executeUpdate(String.format("DELETE FROM course_enrollment WHERE student_id='%d' AND course_id='%d';", studentId, courseId));
    }

    public void insertGroupData(String groupName) throws SQLException {
        statement.executeUpdate(String.format("INSERT INTO groups (group_name) VALUES ('%s');", groupName));
    }
    public void createManyToManyTable() throws SQLException {
        statement.executeUpdate("CREATE TABLE course_enrollment(" +
                "student_id INT," +
                "course_id INT," +
                "FOREIGN KEY (student_id) REFERENCES students(student_id)," +
                "FOREIGN KEY (course_id) REFERENCES courses(course_id)," +
                "UNIQUE (student_id, course_id));");
    }

    public void insertManyToManyData(int studentId, int courseId) throws SQLException {
        statement.executeUpdate(String.format("INSERT INTO course_enrollment (student_id, course_id) VALUES ('%d', '%d');", studentId, courseId));
    }


    public void insertStudentData(String firstName, String lastName, int groupId) throws SQLException {
        statement.executeUpdate(String.format("INSERT INTO students (group_id, first_name, last_name) " +
                "VALUES ('%d', '%s', '%s');", groupId, firstName, lastName));
    }

    public void insertStudentDataWithoutGroupId(String firstName, String lastName) throws SQLException {
        statement.executeUpdate(String.format("INSERT INTO students (first_name, last_name) " +
                "VALUES ('%s', '%s');", firstName, lastName));
    }

    public void insertCourseData(String courseName, String courseDescription) throws SQLException {
        statement.executeUpdate(String.format("INSERT INTO courses (course_name, course_description) " +
                "VALUES ('%s', '%s');", courseName, courseDescription));
    }

    public void clearTable(String tableName) throws SQLException {
        statement.executeUpdate(String.format("TRUNCATE TABLE %s RESTART IDENTITY CASCADE;", tableName));
    }

    //получение всей таблицы.
    public void getGroupData(String groupName) {
        try (ResultSet rs = statement.executeQuery(String.format("SELECT  * FROM %s;", groupName))) {
            while (rs.next()) {
                System.out.println(rs.getInt("group_id") + " " +
                        rs.getString("group_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getStudentsFromCourse(String courseName) {
        String request = String.format("SELECT * " +
                "FROM students " +
                "WHERE student_id = ANY (SELECT student_id FROM course_enrollment WHERE course_id = " +
                "ANY (SELECT course_id FROM courses WHERE course_name = '%s'));", courseName);

        try (ResultSet rs = statement.executeQuery(request)) {
            while (rs.next()) {
                System.out.println(rs.getString("student_id") + "-" + rs.getString("first_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //домучать этот метод
    public void getGroupWithLessStudents() {
        String request = "SELECT group_id, COUNT(group_id) AS group_count " +
                        "FROM students " +
                        "WHERE group_id IS NOT NULL " +
                        "GROUP BY group_id ";
        String finalResult = "SELECT group_count, MIN(temp.group_count) AS min_count FROM (" + request + ") temp WHERE temp.rank()";
        String finalResult2 = "SELECT DISTINCT group_id, MIN(t.group_count) AS min FROM (SELECT group_id, COUNT(group_id) AS group_count FROM students " +
                "WHERE group_id IS NOT NULL GROUP BY group_id) AS t GROUP BY group_id ORDER BY MIN(t.group_count) ASC";
        try (ResultSet rs = statement.executeQuery(finalResult2)) {
            while (rs.next()) {
                System.out.println(rs.getString("group_id") + ":" + rs.getString("min"));
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
