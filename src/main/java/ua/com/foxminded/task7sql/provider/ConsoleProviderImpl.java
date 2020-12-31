package ua.com.foxminded.task7sql.provider;

import ua.com.foxminded.task7sql.DBConnector;
import ua.com.foxminded.task7sql.StudentsDao;
import ua.com.foxminded.task7sql.StudentsDaoImpl;

import java.util.Scanner;

public class ConsoleProviderImpl implements ConsoleProvider {

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите название таблицы, с которой хотите соединиться или 'exit' для завершения программы:");
            String temp = scanner.nextLine().toLowerCase();
            switch (temp) {
                case "students":
                    StudentsDao tempDao = studentsDaoConnection();

                    break;
                case "courses" :

                    break;
                case "groups" :

                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Ошибка при вводе данных");
            }
        }
    }


    private StudentsDao studentsDaoConnection() {
       return new StudentsDaoImpl(new DBConnector("config"));
    }

    private void groupsDaoConnection() {

    }

}
