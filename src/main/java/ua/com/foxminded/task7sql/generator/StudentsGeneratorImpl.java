package ua.com.foxminded.task7sql.generator;

import ua.com.foxminded.task7sql.domain.Student;
import ua.com.foxminded.task7sql.validator.FileNotFoundRuntimeException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentsGeneratorImpl implements StudentsGenerator {
    private static final List<String> FIRST_NAMES;
    private static final List<String> LAST_NAMES;
    private static final Random RANDOM = new Random();
    private static final int NUMBER_ZERO = 0;
    private static final int NUMBER_ONE = 1;
    private static final int NUMBER_TEN = 10;
    private static final int NUMBER_ELEVEN = 11;
    private static final int NUMBER_TWENTY_ONE = 21;
    private static final int NUMBER_TWO_HUNDRED = 200;
    private static final String FILE_READ_ERROR = "File read error";
    private static final String FIRST_NAME_PATH = "src\\main\\resources\\firstName.txt";
    private static final String LAST_NAME_PATH = "src\\main\\resources\\lastName.txt";

    static {
        FIRST_NAMES = listOfFirstNames();
        LAST_NAMES = listOfLastNames();
    }

    @Override
    public List<Student> generateStudents() {
        final List<Student> studentsList = new ArrayList<>();
        int[] numbersOfStudentsForEveryGroup = new int[NUMBER_ELEVEN];
        numbersOfStudentsForEveryGroup[NUMBER_ZERO] = NUMBER_ZERO;

        for (int i = 1; i < NUMBER_ELEVEN; i++) {
            numbersOfStudentsForEveryGroup[i] = RANDOM.nextInt(NUMBER_TWENTY_ONE) + NUMBER_TEN;
        }

        mark: while (studentsList.size() < NUMBER_TWO_HUNDRED) {
            String firstName = getRandomFirstName();
            String lastName = getRandomLastName();

            for (Student temp : studentsList) {
                if (temp.getFirstName().equals(firstName) &&
                        temp.getLastName().equals(lastName)) {
                    continue mark;
                }
            }

            int groupId = RANDOM.nextInt(NUMBER_TEN) + NUMBER_ONE;

            Student student = Student.builder()
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .build();

            if (numbersOfStudentsForEveryGroup[groupId] != NUMBER_ZERO) {
                student.setGroupId(groupId);
                numbersOfStudentsForEveryGroup[groupId]--;
            } else {
                student.setGroupId(NUMBER_ZERO);
            }
            studentsList.add(student);
        }

        return studentsList;
    }

    private String getRandomFirstName() {
        return FIRST_NAMES.get(RANDOM.nextInt(FIRST_NAMES.size()));
    }

    private String getRandomLastName() {
        return LAST_NAMES.get(RANDOM.nextInt(LAST_NAMES.size()));
    }

    private static List<String> listOfFirstNames() {
        final Path abbr = new File(FIRST_NAME_PATH).toPath();

        try (Stream<String> fileStream = Files.newBufferedReader(abbr).lines()) {
            return fileStream.collect(Collectors.toList());
        } catch (IOException e) {
            throw new FileNotFoundRuntimeException(FILE_READ_ERROR, e);
        }
    }

    private static List<String> listOfLastNames() {
        final Path abbr = new File(LAST_NAME_PATH).toPath();

        try (Stream<String> fileStream = Files.newBufferedReader(abbr).lines()) {
            return fileStream.collect(Collectors.toList());
        } catch (IOException e) {
            throw new FileNotFoundRuntimeException(FILE_READ_ERROR, e);
        }
    }
}
