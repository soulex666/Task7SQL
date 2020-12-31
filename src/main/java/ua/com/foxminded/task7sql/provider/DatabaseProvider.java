package ua.com.foxminded.task7sql.provider;

import ua.com.foxminded.task7sql.dbconnectorjdbc.DBConnectorJDBC;
import ua.com.foxminded.task7sql.domain.Course;
import ua.com.foxminded.task7sql.domain.Group;
import ua.com.foxminded.task7sql.domain.Student;

import java.util.List;

public interface DatabaseProvider {
    void clearAndSetDataToCourseTable(DBConnectorJDBC connector, List<Course> courses);

    void clearAndSetDataToStudentsTable(DBConnectorJDBC connector, List<Student> students);

    void clearAndSetDataToGroupTable(DBConnectorJDBC connector, List<Group> groups);

    void clearAndSetRandomDataToStudentsEnrollmentTable(DBConnectorJDBC connector);
}
