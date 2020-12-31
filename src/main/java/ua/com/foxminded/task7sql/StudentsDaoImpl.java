package ua.com.foxminded.task7sql;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class StudentsDaoImpl extends AbstractCrudDaoImpl implements StudentsDao{

    public StudentsDaoImpl(DBConnector connector) {
        super(connector);
    }

    @Override
    public Optional getById(Object o) {
        return Optional.empty();
    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public void save(Object o) {

    }

    @Override
    public void update(Object o, String[] params) {

    }

    @Override
    public void deleteById(Object t) {

    }

    @Override
    public void deleteAllById(Set t) {

    }
}
