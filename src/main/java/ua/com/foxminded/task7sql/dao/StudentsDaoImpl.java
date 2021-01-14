package ua.com.foxminded.task7sql.dao;

import ua.com.foxminded.task7sql.connector.DBConnector;
import ua.com.foxminded.task7sql.domain.Student;
import ua.com.foxminded.task7sql.validator.DBRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentsDaoImpl extends AbstractCrudDaoImpl<Student> implements StudentsDao {
    private static final String CLEAR_TABLE = "TRUNCATE TABLE students RESTART IDENTITY CASCADE;";
    private static final String CLEAR_COURSE_ENROLLMENT_TABLE = "TRUNCATE TABLE course_enrollment;";
    private static final String SET = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";
    private static final String GET_BY_ID = "SELECT * FROM students WHERE student_id = ?";
    private static final String GET_ALL = "SELECT  * FROM students;";
    private static final String UPDATE =
            "UPDATE students " +
                    "SET first_name = ?, last_name = ?, group_id = ?" +
                    "WHERE student_id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM students WHERE student_id = ?;";
    private static final String GET_STUDENTS_FROM_COURSE = "SELECT * " +
            "FROM students " +
            "WHERE student_id = ANY (" +
            "SELECT student_id " +
            "FROM course_enrollment " +
            "WHERE course_id = ANY (" +
            "SELECT course_id " +
            "FROM courses " +
            "WHERE course_name = ?)) " +
            "ORDER BY student_id ASC;";
    private static final String SET_GROUP_ID_TO_STUDENT_BY_ID =
            "UPDATE students " +
                    "SET group_id = ? WHERE student_id = ?;";
    private static final String SET_COURSE_ENROLLMENT_DATA =
            "INSERT INTO course_enrollment (student_id, course_id) " +
                    "VALUES (?, ?);";


    public StudentsDaoImpl(DBConnector connector) {
        super(connector, CLEAR_TABLE, SET, GET_BY_ID, GET_ALL, UPDATE, DELETE_BY_ID);
    }


    @Override
    protected void insert(PreparedStatement preparedStatement, Student entity) throws SQLException {
        preparedStatement.setString(1, entity.getFirstName());
        preparedStatement.setString(2, entity.getLastName());
    }

    @Override
    protected void insertAll(PreparedStatement preparedStatement, List<Student> entity) throws SQLException {
        clearTable();
        for (Student student : entity) {
            insert(preparedStatement, student);
            preparedStatement.addBatch();
        }
    }

    @Override
    protected Student mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Student.builder()
                .withStudentId(resultSet.getInt("student_id"))
                .withFirstName(resultSet.getString("first_name"))
                .withLastName(resultSet.getString("last_name"))
                .withGroupId(resultSet.getInt("group_id"))
                .build();
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, Student entity) throws SQLException {
        preparedStatement.setInt(4, entity.getStudentId());
        preparedStatement.setString(1, entity.getFirstName());
        preparedStatement.setString(2, entity.getLastName());
        preparedStatement.setInt(3, entity.getGroupId());
    }

    @Override
    public List<Student> getStudentsFromCourseByCourseName(String courseName) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_STUDENTS_FROM_COURSE)) {
            preparedStatement.setString(1, courseName);
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Student> entities = new ArrayList<>();
                while (resultSet.next()) {
                    entities.add(mapResultSetToEntity(resultSet));
                }
                return entities;
            }
        } catch (SQLException e) {
            throw new DBRuntimeException("Get table is failed", e);
        }
    }

    @Override
    public void setGroupIdToStudent(Integer studentId, Integer groupId) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_GROUP_ID_TO_STUDENT_BY_ID)) {
            preparedStatement.setInt(1, groupId);
            preparedStatement.setInt(2, studentId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBRuntimeException("Insertion is failed", e);
        }
    }

    @Override
    public void setCourseEnrollmentData(Integer studentId, Integer courseId) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SET_COURSE_ENROLLMENT_DATA)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBRuntimeException("Insertion is failed", e);
        }
    }

    @Override
    public void clearCourseEnrollmentTable() {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CLEAR_COURSE_ENROLLMENT_TABLE)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBRuntimeException("Some problem to clean data from table", e);
        }
    }
}
