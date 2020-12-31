package ua.com.foxminded.task7sql;

import ua.com.foxminded.task7sql.provider.ConsoleProvider;
import ua.com.foxminded.task7sql.provider.ConsoleProviderImpl;

public class NewApplication {
    public static void main(String[] args) {
        ConsoleProvider provider = new ConsoleProviderImpl();
        provider.run();

    }
}
