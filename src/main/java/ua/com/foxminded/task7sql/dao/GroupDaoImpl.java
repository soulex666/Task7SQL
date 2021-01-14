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

public class GroupDaoImpl extends AbstractCrudDaoImpl<Group> implements GroupDao {
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
    public void clearAndSetDataToGroupTable(List<Group> groups) {
        clearTable();
        for (Group group : groups) {
            set(group);
        }
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Group entity) throws SQLException {
        preparedStatement.setString(1, entity.getGroupName());
    }

    @Override
    protected void insertAll(PreparedStatement preparedStatement, List<Group> entity) throws SQLException {
        clearTable();
        for (Group group : entity) {
            insert(preparedStatement, group);
            preparedStatement.addBatch();
        }
    }

    @Override
    protected Group mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Group.builder()
                .withGroupId(resultSet.getInt("group_id"))
                .withGroupName(resultSet.getString("group_name"))
                .build();
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, Group entity) throws SQLException {
        preparedStatement.setInt(2, entity.getGroupId());
        preparedStatement.setString(1, entity.getGroupName());

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
