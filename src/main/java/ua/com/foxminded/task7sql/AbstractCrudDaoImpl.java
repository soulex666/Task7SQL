package ua.com.foxminded.task7sql;

public abstract class AbstractCrudDaoImpl<E> implements CrudDao<E, Long> {
    private final DBConnector connector;


    protected AbstractCrudDaoImpl(DBConnector connector) {
        this.connector = connector;
    }

}
