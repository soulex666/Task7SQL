package ua.com.foxminded.task7sql;

import ua.com.foxminded.task7sql.controller.Controller;
import ua.com.foxminded.task7sql.controller.ControllerImpl;

public class CRUDApplication {
    public static void main(String[] args) {
        Controller controller = new ControllerImpl();
        controller.run();
    }
}
