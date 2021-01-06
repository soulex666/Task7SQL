package ua.com.foxminded.task7sql;

import ua.com.foxminded.task7sql.controller.Controller;
import ua.com.foxminded.task7sql.controller.ControllerImpl;

public class NewApplication {
    public static void main(String[] args) {
        Controller provider = new ControllerImpl();
        provider.run();
    }
}
