package ua.com.foxminded.task7sql.view;

import ua.com.foxminded.task7sql.domain.Group;

import java.util.List;

public class GroupViewImpl implements View<Group> {
    @Override
    public void consoleView(List<Group> entity) {
        if(entity.isEmpty()) {
            System.out.println("Совпадений не найдено");
            return;
        }
        for (Group group : entity) {
            System.out.printf("Group ID: %d%s", group.getGroupId(), NEWLINE_DELIMITER);
            System.out.printf("Group name: %s%s", group.getGroupName(), NEWLINE_DELIMITER);
            System.out.println("------------------------------------------------------");
        }
    }
}
