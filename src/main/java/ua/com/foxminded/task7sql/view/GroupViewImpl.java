package ua.com.foxminded.task7sql.view;

import ua.com.foxminded.task7sql.domain.Group;

public class GroupViewImpl implements View<Group> {
    @Override
    public void consoleView(Group entity) {
        System.out.println("Group ID: " + entity.getGroupId());
        System.out.println("Group name: " + entity.getGroupName());
    }
}
