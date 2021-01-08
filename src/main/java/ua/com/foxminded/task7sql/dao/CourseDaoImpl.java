package ua.com.foxminded.task7sql.dao;

import ua.com.foxminded.task7sql.DBConnector;
import ua.com.foxminded.task7sql.domain.Course;
import ua.com.foxminded.task7sql.domain.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CourseDaoImpl extends AbstractCrudDaoImpl<Course> implements CourseDao{
    private static final String CLEAR_TABLE = "TRUNCATE TABLE courses RESTART IDENTITY CASCADE;";
    private static final String SET = "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";
    private static final String GET_BY_ID = "SELECT * FROM courses WHERE course_id = ?";
    private static final String GET_ALL = "SELECT  * FROM courses;";
    private static final String UPDATE = "UPDATE courses SET (course_name = ?, course_description = ?) " +
            "WHERE course_id  = ?";
    private static final String DELETE_BY_ID = "DELETE FROM courses WHERE course_id = ?;";

    public CourseDaoImpl(DBConnector connector) {
        super(connector,CLEAR_TABLE, SET, SET, GET_BY_ID, GET_ALL, UPDATE, DELETE_BY_ID);
    }

    @Override
    public void clearAndSetDataToCourseTable(List<Course> courses) {
        clearTable();
        for (Course course : courses) {
            set(course);
        }
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Course entity) throws SQLException {
        preparedStatement.setString(1, entity.getCourseName());
        preparedStatement.setString(2, entity.getCourseDescription());
    }

    @Override
    protected void insertAll(PreparedStatement preparedStatement, List<Course> entity) throws SQLException {
        clearTable();
        for (Course course : entity) {
            insert(preparedStatement, course);
            preparedStatement.addBatch();
        }
    }

    @Override
    protected Course mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Course.builder()
                .withCourseId(resultSet.getInt("course_id"))
                .withCourseName(resultSet.getString("course_name"))
                .withCourseDescription(resultSet.getString("course_description"))
                .build();
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, Course entity) throws SQLException {
        preparedStatement.setInt(3, entity.getCourseId());
        preparedStatement.setString(1, entity.getCourseName());
        preparedStatement.setString(2, entity.getCourseDescription());
    }
}
