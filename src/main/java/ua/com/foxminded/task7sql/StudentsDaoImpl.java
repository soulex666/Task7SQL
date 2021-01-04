package ua.com.foxminded.task7sql;

import ua.com.foxminded.task7sql.domain.Student;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class StudentsDaoImpl extends AbstractCrudDaoImpl<Student> implements StudentsDao {
    private static final String SAVE_QUERY = "INSERT INTO users (name, surname,email,password) values(?, ?, ?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT * from topics WHERE id = ?";
    private static final String FIND_ALL_QUERY = "";
    private static final String UPDATE_QUERY = "";
    private static final String DELETE_BY_ID_QUERY = "";

    public StudentsDaoImpl(DBConnector connector) {
        super(connector, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, UPDATE_QUERY, DELETE_BY_ID_QUERY);
    }

    @Override
    public void save(Student entity) {
        super.save(entity);
    }

    @Override
    public Optional<Student> getById(Integer id) {
        return super.getById(id);
    }

    @Override
    public List<Student> getAll() {
        return super.getAll();
    }


    @Override
    public void deleteAllById(Set t) {

    }

    @Override
    public void update(Student entity) {
        super.update(entity);
    }

    @Override
    public void deleteById(Integer id) {
        super.deleteById(id);
    }

}
