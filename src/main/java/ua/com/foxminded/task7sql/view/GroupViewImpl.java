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
            System.out.println("Group ID: " + group.getGroupId());
            System.out.println("Group name: " + group.getGroupName());
        }

    }
}
