--удаление таблиц, если они уже существуют
DROP TABLE IF EXISTS groups, students, courses;

--создаёт таблицу groups
CREATE TABLE groups (
	group_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
	group_name VARCHAR(5) NOT NULL
);

--создаёт таблицу students
CREATE TABLE students (
	student_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
	group_id INT NOT NULL REFERENCES groups (group_id),
	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(20) NOT NULL	
);

--создаёт таблицу courses
CREATE TABLE courses (
	course_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
	course_name VARCHAR(20) NOT NULL,
	course_description VARCHAR(100) NOT NULL
);
