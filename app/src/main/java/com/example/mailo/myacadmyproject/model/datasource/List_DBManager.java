package com.example.mailo.myacadmyproject.model.datasource;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.mailo.myacadmyproject.model.backend.DB_manager;
import com.example.mailo.myacadmyproject.model.entities.Course;
import com.example.mailo.myacadmyproject.model.entities.Student;
import com.example.mailo.myacadmyproject.model.entities.StudentCourse;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ezra Steinmetz on August 2016
 */
public class List_DBManager implements DB_manager {

    static List<Student> students;
    static List<Course> courses;
    static List<StudentCourse> studentCourses;
boolean isUpdate = false;


    static {
        students = new ArrayList<>();
        courses = new ArrayList<>();
        studentCourses = new ArrayList<>();
    }


    @Override
    public boolean removeStudent(long id) {
        Student studentToRemove = null;
        for (Student item : students)
            if (item.getId() == id) {
                studentToRemove = item;
                isUpdate = true;
                break;
            }

        return students.remove(studentToRemove);

    }

    @Override
    public boolean removeCourse(long id) {
        Course courseToRemove = null;
        for (Course item : courses)
            if (item.getId() == id) {
                courseToRemove = item;
                isUpdate = true;
                break;
            }
        return courses.remove(courseToRemove);
    }

    @Override
    public long addStudent(ContentValues values) {

        Student student = Tools.ContentValuesToStudent(values);
        students.add(student);
        isUpdate=true;
        return student.getId();
    }

    @Override
    public long addCourse(ContentValues values) {

        Course course = Tools.ContentValuesToCourse(values);
        courses.add(course);
        isUpdate=true;
        return course.getId();
    }

    @Override
    public Cursor getStudents() {
        return Tools.StudentListToCursor(students);
    }

    @Override
    public long addLecturer(ContentValues values) {
        return 0;
    }

    @Override
    public boolean removeLecturer(long id) {
        return false;
    }

    @Override
    public boolean updateLecturer(long id, ContentValues values) {
        return false;
    }

    @Override
    public Cursor getLecturer() {
        return null;
    }

    @Override
    public Cursor getCourses() {
        return Tools.CourseListToCursor(courses);
    }

    @Override
    public long addStudentCourse(ContentValues values) {
return  -1;
    }

    @Override
    public boolean updateStudent(long id, ContentValues values) {
        Student student = Tools.ContentValuesToStudent(values);
        for (int i = 0; i < students.size(); i++)
            if (students.get(i).getId() == id) {
                students.get(i).setName(student.getName());
                students.get(i).setPhone(student.getPhone());
                isUpdate=true;
                return true;
            }
        return false;
    }

    @Override
    public boolean updateCourse(long id, ContentValues values) {
        Course course = Tools.ContentValuesToCourse(values);
        for (int i = 0; i < courses.size(); i++)
            if (courses.get(i).getId() == id) {
                courses.get(i).setName(course.getName());
                isUpdate=true;
                return true;
            }
        return false;
    }

    @Override
    public boolean isUpdatet() {

        if(isUpdate)
        {
            isUpdate = false;
            return  true;
        }

        return  false;


    }
}