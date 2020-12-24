package ua.com.foxminded.task7sql.generator;

import ua.com.foxminded.task7sql.domain.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseCreatorImpl implements CourseCreator{

    @Override
    public List<Course> getCourses() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(Course.builder()
                .withCourseId(1)
                .withCourseName("math")
                .withCourseDescription("Mathematics").build());
        courseList.add(Course.builder()
                .withCourseId(2)
                .withCourseName("econ")
                .withCourseDescription("Economics").build());
        courseList.add(Course.builder()
                .withCourseId(3)
                .withCourseName("med")
                .withCourseDescription("Medicine").build());
        courseList.add(Course.builder()
                .withCourseId(4)
                .withCourseName("cs")
                .withCourseDescription("Computer Science").build());
        courseList.add(Course.builder()
                .withCourseId(5)
                .withCourseName("geog")
                .withCourseDescription("Geography").build());
        courseList.add(Course.builder()
                .withCourseId(6)
                .withCourseName("phil")
                .withCourseDescription("Philosophy").build());
        courseList.add(Course.builder()
                .withCourseId(7)
                .withCourseName("biol")
                .withCourseDescription("Biology").build());
        courseList.add(Course.builder()
                .withCourseId(8)
                .withCourseName("lsj")
                .withCourseDescription("Law, Societies, and Justice").build());
        courseList.add(Course.builder()
                .withCourseId(9)
                .withCourseName("ling")
                .withCourseDescription("Linguistics").build());
        courseList.add(Course.builder()
                .withCourseId(10)
                .withCourseName("eng")
                .withCourseDescription("Engineering").build());
        return courseList;
    }
}
