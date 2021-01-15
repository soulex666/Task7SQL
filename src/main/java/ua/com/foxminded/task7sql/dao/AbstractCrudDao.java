package ua.com.foxminded.task7sql.dao;

import ua.com.foxminded.task7sql.connector.DBConnector;
import ua.com.foxminded.task7sql.validator.DBRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudDao<E> implements CrudDao<E, Integer> {
    protected final DBConnector connector;
    private final String set;
    private final String clearTable;
    private final String getById;
    private final String getAll;
    private final String update;
    private final String deleteById;

    protected AbstractCrudDao(DBConnector connector, String clearTable, String set, String getById,
                              String getAll, String update, String deleteById) {
        this.connector = connector;
        this.clearTable = clearTable;
        this.set = set;
        this.getById = getById;
        this.getAll = getAll;
        this.update = update;
        this.deleteById = deleteById;
    }

    @Override
    public void clearTable() {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(clearTable)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBRuntimeException("Some problem to clean data from table", e);
        }
    }

    @Override
    public void set(E entity) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(set)) {
            insert(preparedStatement, entity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBRuntimeException("Insertion is failed", e);
        }
    }

    @Override
    public void setAll(List<E> entity) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(set)) {
            insertAll(preparedStatement, entity);
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new DBRuntimeException("Insertion is failed", e);
        }
    }

    @Override
    public Optional<E> getById(Integer id) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getById)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.ofNullable(mapResultSetToEntity(resultSet)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DBRuntimeException(String.format("Get by ID %d is failed", id), e);
        }
    }

    @Override
    public List<E> getAll() {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAll)) {
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<E> entities = new ArrayList<>();
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
    public void update(E entity) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            updateValues(preparedStatement, entity);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBRuntimeException("Update is failed", e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteById)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBRuntimeException(String.format("Delete by ID %d is failed", id), e);
        }
    }

    protected abstract void insert(PreparedStatement preparedStatement, E entity);

    protected abstract void insertAll(PreparedStatement preparedStatement, List<E> entity);

    protected abstract E mapResultSetToEntity(ResultSet resultSet);

    protected abstract void updateValues(PreparedStatement preparedStatement, E entity);
}
