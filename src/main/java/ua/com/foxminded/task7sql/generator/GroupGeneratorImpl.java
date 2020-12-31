package ua.com.foxminded.task7sql.generator;

import ua.com.foxminded.task7sql.domain.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupGeneratorImpl implements GroupGenerator {
    private static final Random RANDOM = new Random();
    private static final String DASH_DELIMITER = "-";


    @Override
    public List<Group> generateGroups() {
        List<Group> tempGroup = new ArrayList<>();
        while (tempGroup.size() < 10) {
            Group group = Group.builder()
                    .withGroupName(generateRandomGroupName())
                    .build();
            if (!tempGroup.contains(group)) {
                tempGroup.add(group);
            }
        }

        return tempGroup;
    }

    private String generateRandomGroupName() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < 2; i++) {
            char tmp = (char)('a' + RANDOM.nextInt('z' - 'a'));
            sb.append(tmp);
        }

        sb.append(DASH_DELIMITER);

        for(int i = 0; i < 2; i++) {
            int tmp =RANDOM.nextInt(10);
            sb.append(tmp);
        }

        return sb.toString();
    }
}
