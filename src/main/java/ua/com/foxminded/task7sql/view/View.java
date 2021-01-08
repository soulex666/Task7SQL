package ua.com.foxminded.task7sql.view;


import java.util.List;

public interface View<E> {

    void consoleView(List<E> entity);
}
