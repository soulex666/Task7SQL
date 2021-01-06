package ua.com.foxminded.task7sql.dao;

import ua.com.foxminded.task7sql.DBConnector;
import ua.com.foxminded.task7sql.domain.Group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupDaoImpl extends AbstractCrudDaoImpl<Group> implements GroupDao {
    private static final String SAVE = "INSERT INTO groups (group_name) VALUES (?)";
    private static final String GET_BY_ID = "SELECT * FROM groups WHERE group_id = ?";
    private static final String GET_ALL = "SELECT  * FROM groups;";
    private static final String UPDATE = "UPDATE groups SET group_name = ? " +
            "WHERE group_id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM groups WHERE group_id = ?;";

    public GroupDaoImpl(DBConnector connector) {
        super(connector, SAVE, GET_BY_ID, GET_ALL, UPDATE, DELETE_BY_ID);
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Group entity) throws SQLException {
        preparedStatement.setString(1, entity.getGroupName());
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
}
