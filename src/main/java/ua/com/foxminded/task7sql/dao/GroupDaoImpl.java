package ua.com.foxminded.task7sql.dao;

import ua.com.foxminded.task7sql.connector.DBConnector;
import ua.com.foxminded.task7sql.domain.Group;
import ua.com.foxminded.task7sql.validator.DBRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDaoImpl extends AbstractCrudDao<Group> implements GroupDao {
    private static final String CLEAR_TABLE = "TRUNCATE TABLE groups RESTART IDENTITY CASCADE;";
    private static final String SET = "INSERT INTO groups (group_name) VALUES (?)";
    private static final String GET_BY_ID = "SELECT * FROM groups WHERE group_id = ?";
    private static final String GET_ALL = "SELECT  * FROM groups;";
    private static final String UPDATE = "UPDATE groups SET group_name = ? " +
            "WHERE group_id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM groups WHERE group_id = ?;";
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

    public GroupDaoImpl(DBConnector connector) {
        super(connector, CLEAR_TABLE, SET, GET_BY_ID, GET_ALL, UPDATE, DELETE_BY_ID);
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Group entity) {
        try {
            preparedStatement.setString(1, entity.getGroupName());
        } catch (SQLException e) {
            throw new DBRuntimeException("Incorrect inserts group data", e);
        }
    }

    @Override
    protected void insertAll(PreparedStatement preparedStatement, List<Group> entity) {
        try {
            for (Group group : entity) {
                insert(preparedStatement, group);
                preparedStatement.addBatch();
            }
        } catch (SQLException e) {
            throw new DBRuntimeException("Incorrect inserts group data", e);
        }
    }

    @Override
    protected Group mapResultSetToEntity(ResultSet resultSet) {
        try {
            return Group.builder()
                    .withGroupId(resultSet.getInt("group_id"))
                    .withGroupName(resultSet.getString("group_name"))
                    .build();
        } catch (SQLException e) {
            throw new DBRuntimeException("ResultSet reading error", e);
        }
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, Group entity)  {
        try {
            preparedStatement.setInt(2, entity.getGroupId());
            preparedStatement.setString(1, entity.getGroupName());
        } catch (SQLException e) {
            throw new DBRuntimeException("Updating data error", e);
        }
    }

    @Override
    public List<Group> getGroupsWithMinimumStudents() {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_GROUPS_WITH_MINIMUM_STUDENTS)) {

            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Group> entities = new ArrayList<>();
                while (resultSet.next()) {
                    entities.add(getById(resultSet.getInt("group_id")).get());
                }
                return entities;
            }
        } catch (SQLException e) {
            throw new DBRuntimeException("Get table is failed", e);
        }
    }
}
