package ua.com.foxminded.task7sql.dao;

import ua.com.foxminded.task7sql.DBConnector;
import ua.com.foxminded.task7sql.domain.Course;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseDaoImpl extends AbstractCrudDaoImpl<Course> implements CourseDao{
    private static final String SAVE = "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";
    private static final String GET_BY_ID = "SELECT * FROM courses WHERE course_id = ?";
    private static final String GET_ALL = "SELECT  * FROM courses;";
    private static final String UPDATE = "UPDATE courses SET (course_name = ?, course_description = ?) " +
            "WHERE course_id  = ?";
    private static final String DELETE_BY_ID = "DELETE FROM courses WHERE course_id = ?;";

    public CourseDaoImpl(DBConnector connector) {
        super(connector, SAVE, GET_BY_ID, GET_ALL, UPDATE, DELETE_BY_ID);
    }
    @Override
    protected void insert(PreparedStatement preparedStatement, Course entity) throws SQLException {
        preparedStatement.setString(1, entity.getCourseName());
        preparedStatement.setString(2, entity.getCourseDescription());
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
