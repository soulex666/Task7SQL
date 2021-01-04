package ua.com.foxminded.task7sql;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CrudDao<T, ID> {
    Optional<T> getById(ID id);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void deleteById(ID t);

    void deleteAllById(Set<ID> t);

}
