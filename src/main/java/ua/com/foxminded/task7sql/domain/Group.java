package ua.com.foxminded.task7sql.domain;

import java.util.Objects;

public class Group {
    private final int groupId;
    private final String groupName;

    private Group(Builder builder) {
        this.groupId = builder.groupId;
        this.groupName = builder.groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getGroupId() {
        return groupId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int groupId;
        private String groupName;

        private Builder() {
        }

        public Group build() {
            return new Group(this);
        }

        public Builder withGroupId(int groupId) {
            this.groupId = groupId;
            return this;
        }

        public Builder withGroupName(String groupName) {
            this.groupName = groupName;
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Group group = (Group) o;

        return (this.groupId == group.groupId) &&
                Objects.equals(this.groupName, group.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, groupName);
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
