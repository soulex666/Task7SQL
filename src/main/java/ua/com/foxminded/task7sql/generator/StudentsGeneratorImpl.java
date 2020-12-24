package ua.com.foxminded.task7sql.generator;

import ua.com.foxminded.task7sql.domain.Student;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentsGeneratorImpl implements StudentsGenerator{
    private static final List<String> FIRST_NAMES;
    private static final List<String> LAST_NAMES;
    private static final Random RANDOM = new Random();
    static {
        FIRST_NAMES = listOfFirstNames();
        LAST_NAMES = listOfLastNames();
    }

    @Override
    public List<Student> generateStudents() {
        final List<Student> tempStudents = new ArrayList<>();
        int[] numbersOfStudentsForEveryGroup = new int[11];
        numbersOfStudentsForEveryGroup[0] = 0;
        for (int i = 1; i < 11; i++) {
            numbersOfStudentsForEveryGroup[i] = RANDOM.nextInt(21) + 10;
        }

        while (tempStudents.size() < 200) {
            String firstName = getRandomFirstName();
            String lastName = getRandomLastName();

            int groupId = RANDOM.nextInt(10) + 1;

            Student student = new Student(firstName, lastName);

            if (!tempStudents.contains(student)) {
                if (numbersOfStudentsForEveryGroup[groupId] != 0) {
                    student.setGroup_id(groupId);
                    numbersOfStudentsForEveryGroup[groupId]--;
                } else {
                    student.setGroup_id(0);
                }
                tempStudents.add(student);
            }
        }
        return tempStudents;
    }

    private String getRandomFirstName() {
        return FIRST_NAMES.get(RANDOM.nextInt(FIRST_NAMES.size()));
    }

    private String getRandomLastName() {
        return LAST_NAMES.get(RANDOM.nextInt(LAST_NAMES.size()));
    }

    private static List<String> listOfFirstNames() {
        String filePath = "src\\main\\resources\\firstName.txt";
        final Path abbr = new File(filePath).toPath();
        try (Stream<String> fileStream = Files.newBufferedReader(abbr).lines()) {
            return fileStream.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private static List<String> listOfLastNames() {
        String filePath = "src\\main\\resources\\lastName.txt";
        final Path abbr = new File(filePath).toPath();
        try (Stream<String> fileStream = Files.newBufferedReader(abbr).lines()) {
            return fileStream.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
