package com.example.mailo.myacadmyproject.model.backend;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;


import com.example.mailo.myacadmyproject.model.entities.Course;
import com.example.mailo.myacadmyproject.model.entities.Student;

import java.util.List;

/**
 * Created by Ezra Steinmetz on August 2016
 */
public interface DB_manager {

    long addStudent(ContentValues values);
    boolean removeStudent(long id);
    boolean updateStudent(long id, ContentValues values);
    Cursor getStudents();

    long addLecturer(ContentValues values);
    boolean removeLecturer(long id);
    boolean updateLecturer(long id, ContentValues values);
    Cursor getLecturer();

    long addCourse(ContentValues values);
    boolean removeCourse(long id);
    boolean updateCourse(long id, ContentValues values);
    Cursor getCourses();

     long addStudentCourse(ContentValues values);

    boolean isUpdatet();

}
