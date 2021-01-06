package ua.com.foxminded.task7sql.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T, ID> {
    Optional<T> getById(ID id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void deleteById(ID t);

}
