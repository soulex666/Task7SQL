package ua.com.foxminded.task7sql.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class CourseEnrollmentGeneratorImpl implements CourseEnrollmentGenerator{
    private static final Random RANDOM = new Random();
    private static final int NUMBER_ONE = 1;
    private static final int NUMBER_THREE = 3;
    private static final int NUMBER_TEN = 10;
    private static final int NUMBER_TWO_HUNDRED = 200;

    @Override
    public Map<Integer, List<Integer>> getCourseEnrollmentData() {
        Map<Integer, List<Integer>> dataMap = new HashMap<>();

        for (int i = 1; i <= NUMBER_TWO_HUNDRED; i++) {
            int numberOfCourses = RANDOM.nextInt(NUMBER_THREE) + NUMBER_ONE;
            final Set<Integer> tempSet = new HashSet<>(NUMBER_THREE);
            final List<Integer> tempList = new ArrayList<>(NUMBER_THREE);
            for (int j = 0; j < numberOfCourses; j++) {
                int randomCourseId = RANDOM.nextInt(NUMBER_TEN) + NUMBER_ONE;
                while (tempSet.contains(randomCourseId)) {
                    randomCourseId = RANDOM.nextInt(NUMBER_TEN) + NUMBER_ONE;
                }
                tempList.add(randomCourseId);
                tempSet.add(randomCourseId);
            }
            dataMap.put(i, tempList);
        }

        return dataMap;
    }
}
