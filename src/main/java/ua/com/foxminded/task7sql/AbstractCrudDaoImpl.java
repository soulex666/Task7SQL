package ua.com.foxminded.task7sql;

import ua.com.foxminded.task7sql.validator.DBRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractCrudDaoImpl<E> implements CrudDao<E, Integer> {
    private final DBConnector connector;
    private final String save;
    private final String getById;
    private final String getAll;
    private final String update;
    private final String deleteById;


    protected AbstractCrudDaoImpl(DBConnector connector, String save, String getById,
                                  String getAll, String update, String deleteById) {
        this.connector = connector;
        this.save = save;
        this.getById = getById;
        this.getAll = getAll;
        this.update = update;
        this.deleteById = deleteById;
    }

    @Override
    public void save(E entity) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(save)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
//            LOGGER.error("Insertion is failed", e);
            throw new DBRuntimeException("Insertion is failed", e);
        }
    }

    @Override
    public Optional<E> getById(Integer id) {
        return null;
    }

    @Override
    public List<E> getAll() {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAll)) {
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                List<E> entities = new ArrayList<>();
                while (resultSet.next()) {

                }
                return entities;
            }
        } catch (SQLException e) {
            throw new DBRuntimeException(e);
        }
    }

    @Override
    public void update(E entity) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(update)) {



            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DBRuntimeException(e);
        }
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException();
    }


}
