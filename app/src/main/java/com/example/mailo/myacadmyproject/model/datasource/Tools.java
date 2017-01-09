package com.example.mailo.myacadmyproject.model.datasource;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;

import com.example.mailo.myacadmyproject.model.entities.Course;
import com.example.mailo.myacadmyproject.model.entities.Lecturer;
import com.example.mailo.myacadmyproject.model.entities.Student;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import com.example.mailo.myacadmyproject.model.backend.AcademyContract;
import com.example.mailo.myacadmyproject.model.entities.StudentCourse;
import com.example.mailo.myacadmyproject.model.entities.Year;


/**
 * Created by mailo on 26/11/2016.
 */

public class Tools {

    public static ContentValues StudentToContentValues(Student student) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(AcademyContract.Student.ID, student.getId());
        contentValues.put(AcademyContract.Student.NAME, student.getName());
        contentValues.put(AcademyContract.Student.PHONE, student.getPhone());
        contentValues.put(AcademyContract.Student.YEAR, student.getYear().toString());

        return contentValues;
    }

    public static ContentValues LecturerToContentValues(Lecturer lecturer) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(AcademyContract.Lecturer.ID, lecturer.getId());
        contentValues.put(AcademyContract.Lecturer.NAME, lecturer.getName());
        contentValues.put(AcademyContract.Lecturer.PHONE, lecturer.getPhone());
        contentValues.put(AcademyContract.Lecturer.SENIORITY, lecturer.getSeniority());

        return contentValues;
    }

    public static ContentValues CourseToContentValues(Course course) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(AcademyContract.Course.ID, course.getId());
        contentValues.put(AcademyContract.Course.NAME, course.getName());
        contentValues.put(AcademyContract.Course.MIN_GRADE, course.getMinGrade());
        contentValues.put(AcademyContract.Course.REQUIRED, course.isRequired());
        contentValues.put(AcademyContract.Course.LECTURER_ID, course.getLecturerId());

        return contentValues;
    }

    public static ContentValues StudentCourseToContentValues(StudentCourse studentCourse) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(AcademyContract.StudentCourse.ID, studentCourse.getId());
        contentValues.put(AcademyContract.StudentCourse.COURSE_ID, studentCourse.getCourseId());
        contentValues.put(AcademyContract.StudentCourse.STUDENT_ID, studentCourse.getStudentId());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // like MySQL Format
        String dateString = dateFormat.format(studentCourse.getRegDate());
        contentValues.put(AcademyContract.StudentCourse.REG_DATE, dateString);

        return contentValues;
    }



    public static Student ContentValuesToStudent(ContentValues contentValues) {

        Student student = new Student();
        student.setId(contentValues.getAsLong(AcademyContract.Student.ID));
        student.setName(contentValues.getAsString(AcademyContract.Student.NAME));
        student.setPhone(contentValues.getAsString(AcademyContract.Student.PHONE));
        student.setYear(Year.valueOf(contentValues.getAsString(AcademyContract.Student.YEAR)));

        return student;
    }

    public static Lecturer ContentValuesToLecturer(ContentValues contentValues) {

        Lecturer lecturer = new Lecturer();
        lecturer.setId(contentValues.getAsLong(AcademyContract.Lecturer.ID));
        lecturer.setName(contentValues.getAsString(AcademyContract.Lecturer.NAME));
        lecturer.setPhone(contentValues.getAsString(AcademyContract.Lecturer.PHONE));
        lecturer.setSeniority(contentValues.getAsInteger(AcademyContract.Lecturer.SENIORITY));

        return lecturer;
    }

    public static Course ContentValuesToCourse(ContentValues contentValues) {
        Course course = new Course();
        course.setId(contentValues.getAsLong(AcademyContract.Course.ID));
        course.setName(contentValues.getAsString(AcademyContract.Course.NAME));
        course.setMinGrade(contentValues.getAsInteger(AcademyContract.Course.MIN_GRADE));
        course.setRequired(contentValues.getAsBoolean(AcademyContract.Course.REQUIRED));
        course.setLecturerId(contentValues.getAsLong(AcademyContract.Course.LECTURER_ID));


        return course;
    }

    public static StudentCourse ContentValuesToStudentCourse(ContentValues contentValues) {

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setId(contentValues.getAsLong(AcademyContract.StudentCourse.ID));
        studentCourse.setCourseId(contentValues.getAsLong(AcademyContract.StudentCourse.COURSE_ID));
        studentCourse.setStudentId(contentValues.getAsLong(AcademyContract.StudentCourse.STUDENT_ID));

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // like MySQL Format
        String dateString = contentValues.getAsString(AcademyContract.StudentCourse.REG_DATE);
        try {
            studentCourse.setRegDate(dateFormat.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return studentCourse;
    }



    public static Cursor StudentListToCursor(List<Student> students) {
        String[] columns = new String[]
                {
                        AcademyContract.Student.ID,
                        AcademyContract.Student.NAME,
                        AcademyContract.Student.PHONE,
                        AcademyContract.Student.YEAR
                };

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Student s : students) {
            matrixCursor.addRow(new Object[]
                    {
                            s.getId(),
                            s.getName(),
                            s.getPhone(),
                            s.getYear()
                    });
        }

        return matrixCursor;
    }

    public static Cursor LecturerListToCursor(List<Lecturer> lecturers) {
        String[] columns = new String[]
                {
                        AcademyContract.Lecturer.ID,
                        AcademyContract.Lecturer.NAME,
                        AcademyContract.Lecturer.PHONE,
                        AcademyContract.Lecturer.SENIORITY
                };

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Lecturer lecturer : lecturers) {
            matrixCursor.addRow(new Object[]
                    {
                            lecturer.getId(),
                            lecturer.getName(),
                            lecturer.getPhone(),
                            lecturer.getSeniority()
                    });
        }

        return matrixCursor;
    }

    public static Cursor CourseListToCursor(List<Course> courses) {
        String[] columns = new String[]
                {
                        AcademyContract.Course.ID,
                        AcademyContract.Course.NAME,
                        AcademyContract.Course.MIN_GRADE,
                        AcademyContract.Course.REQUIRED,
                        AcademyContract.Course.LECTURER_ID

                };

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (Course c : courses) {
            matrixCursor.addRow(new Object[]
                    {
                            c.getId(),
                            c.getName(),
                            c.getMinGrade(),
                            c.isRequired(),
                            c.getLecturerId()
                    });
        }

        return matrixCursor;
    }

    public static Cursor StudentCourseListToCursor(List<StudentCourse> studentCourses) {
        String[] columns = new String[]
                {
                        AcademyContract.StudentCourse.ID,
                        AcademyContract.StudentCourse.STUDENT_ID,
                        AcademyContract.StudentCourse.COURSE_ID,
                        AcademyContract.StudentCourse.REG_DATE,
                };

        MatrixCursor matrixCursor = new MatrixCursor(columns);

        for (StudentCourse studentCourse : studentCourses) {
            matrixCursor.addRow(new Object[]
                    {
                            studentCourse.getId(),
                            studentCourse.getStudentId(),
                            studentCourse.getCourseId(),
                            studentCourse.getRegDate()
                    });
        }

        return matrixCursor;
    }


}
