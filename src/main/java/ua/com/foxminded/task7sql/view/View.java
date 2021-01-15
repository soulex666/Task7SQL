package ua.com.foxminded.task7sql.view;

import java.util.List;

public interface View<E> {
    String NEWLINE_DELIMITER = "\n";

    void consoleView(List<E> entity);
}
