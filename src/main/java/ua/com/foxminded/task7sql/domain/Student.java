package ua.com.foxminded.task7sql.domain;

import java.util.Objects;

public class Student {
    private final int studentId;
    private final String firstName;
    private final String lastName;
    private int groupId;

    private Student(Builder builder) {
        this.studentId = builder.studentId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.groupId = builder.groupId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (studentId != student.studentId) return false;
        if (groupId != student.groupId) return false;
        return (Objects.equals(firstName, student.firstName)) &&
                Objects.equals(lastName, student.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int studentId;
        private String firstName;
        private String lastName;
        private int groupId;

        private Builder() {
        }

        public Student build() {
            return new Student(this);
        }

        public Builder withStudentId(int studentId) {
            this.studentId = studentId;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withGroupId(int groupId) {
            this.groupId = groupId;
            return this;
        }
    }
}
