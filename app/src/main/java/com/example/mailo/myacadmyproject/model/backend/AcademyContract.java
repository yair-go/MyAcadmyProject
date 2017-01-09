package com.example.mailo.myacadmyproject.model.backend;

import android.net.Uri;

import java.util.Date;


/**
 * Created by mailo on 08/12/2016.
 */

public class AcademyContract {

    /**
     * The authority for the contacts provider
     */
    public static final String AUTHORITY = "com.oshri.academy";
    /**
     * A content:// style uri to the authority for the contacts provider
     */
    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    public static class Student {

        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String PHONE = "phone";
        public static final String YEAR = "year";


        /**
         * The content:// style URI for this table
         */
        public static final Uri STUDENT_URI = Uri.withAppendedPath(AUTHORITY_URI, "students");
    }

    public static class Lecturer {

        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String PHONE = "phone";
        public static final String SENIORITY = "seniority";


        /**
         * The content:// style URI for this table
         */
        public static final Uri LECTURER_URI = Uri.withAppendedPath(AUTHORITY_URI, "lecturers");
    }

    public static class Course {
        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String MIN_GRADE = "minGrade";
        public static final String REQUIRED = "required";

        public static final String LECTURER_ID = "lecturerId";

        /**
         * The content:// style URI for this table
         */
        public static final Uri COURSE_URI = Uri.withAppendedPath(AUTHORITY_URI, "courses");
    }

    public static class StudentCourse {
        public static final String ID = "_id";
        public static final String STUDENT_ID = "studentId";
        public static final String COURSE_ID = "courseId";
        public static final String REG_DATE = "regDate";

        /**
         * The content:// style URI for this table
         */
        public static final Uri STUDENT_COURSE_URI = Uri.withAppendedPath(AUTHORITY_URI, "studentCourse");
    }
}
