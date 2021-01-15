package ua.com.foxminded.task7sql.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T, ID> {
    void clearTable();

    Optional<T> getById(ID id);

    List<T> getAll();

    void set(T t);

    void setAll(List<T> ts);

    void update(T t);

    void deleteById(ID t);
}
