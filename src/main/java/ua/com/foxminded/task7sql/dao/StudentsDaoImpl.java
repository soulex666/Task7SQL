package ua.com.foxminded.task7sql.dao;

import ua.com.foxminded.task7sql.DBConnector;
import ua.com.foxminded.task7sql.domain.Student;
import ua.com.foxminded.task7sql.validator.DBRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentsDaoImpl extends AbstractCrudDaoImpl<Student> implements StudentsDao {
    private static final String SAVE = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";
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

    public StudentsDaoImpl(DBConnector connector) {
        super(connector, SAVE, GET_BY_ID, GET_ALL, UPDATE, DELETE_BY_ID);
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Student entity) throws SQLException {
        preparedStatement.setString(1, entity.getFirstName());
        preparedStatement.setString(2, entity.getLastName());
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


}
