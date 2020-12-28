package ua.com.foxminded.task7sql.generator;

import ua.com.foxminded.task7sql.domain.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseCreatorImpl implements CourseCreator{

    @Override
    public List<Course> getCourses() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(Course.builder()
                .withCourseName("math")
                .withCourseDescription("Mathematics").build());
        courseList.add(Course.builder()
                .withCourseName("econ")
                .withCourseDescription("Economics").build());
        courseList.add(Course.builder()
                .withCourseName("med")
                .withCourseDescription("Medicine").build());
        courseList.add(Course.builder()
                .withCourseName("cs")
                .withCourseDescription("Computer Science").build());
        courseList.add(Course.builder()
                .withCourseName("geog")
                .withCourseDescription("Geography").build());
        courseList.add(Course.builder()
                .withCourseName("phil")
                .withCourseDescription("Philosophy").build());
        courseList.add(Course.builder()
                .withCourseName("biol")
                .withCourseDescription("Biology").build());
        courseList.add(Course.builder()
                .withCourseName("lsj")
                .withCourseDescription("Law, Societies, and Justice").build());
        courseList.add(Course.builder()
                .withCourseName("ling")
                .withCourseDescription("Linguistics").build());
        courseList.add(Course.builder()
                .withCourseName("eng")
                .withCourseDescription("Engineering").build());
        return courseList;
    }
}
