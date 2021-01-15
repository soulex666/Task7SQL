package ua.com.foxminded.task7sql.dao;

import ua.com.foxminded.task7sql.domain.Group;

import java.util.List;

public interface GroupDao extends CrudDao<Group, Integer>{
    List<Group> getGroupsWithMinimumStudents();
}
