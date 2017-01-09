package com.example.mailo.myacadmyproject.model.backend;

import android.app.DownloadManager;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import com.example.mailo.myacadmyproject.model.datasource.Tools;
import com.example.mailo.myacadmyproject.model.entities.Course;
import com.example.mailo.myacadmyproject.model.entities.Student;

import java.security.Provider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AcademyContentProvider extends ContentProvider {

    DB_manager manager = DBManagerFactory.getManager();
    final String TAG = "academyContemt";

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete " + uri.toString());

        String listName = uri.getLastPathSegment();
        long id = ContentUris.parseId(uri);
        switch (listName) {
            case "students":   /// content://com.oshri.academy/students/23
                if (manager.removeStudent(id))
                    return 1;
                break;

            case "lecturers":
                if (manager.removeLecturer(id))
                    return 1;
                break;

            case "courses":
                if (manager.removeCourse(id))
                    return 1;
                break;
        }
        return 0;
    }

    @Override
    public String getType(Uri uri) {

        Log.d(TAG, "getType " + uri.toString());
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Log.d(TAG, "insert " + uri.toString());

        String listName = uri.getLastPathSegment();
        long id = -1;
        switch (listName) {
            case "students":
                id = manager.addStudent(values);
                return ContentUris.withAppendedId(uri, id);

            case "lecturers":
                id = manager.addLecturer(values);
                return ContentUris.withAppendedId(uri, id);

            case "courses":
                id = manager.addCourse(values);
                return ContentUris.withAppendedId(uri, id);

        }
        return null;
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate");
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Log.d(TAG, "query " + uri.toString());

        String listName = uri.getLastPathSegment();
        // String s = AcademyContract.Student.STUDENT_URI.getLastPathSegment();
        switch (listName) {
            case "students":
                return manager.getStudents();//

            case "lecturers":
                return manager.getLecturer();//

            case "courses":
                return manager.getCourses();//

        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        Log.d(TAG, "update " + uri.toString());

        String listName = uri.getLastPathSegment();
        long id = ContentUris.parseId(uri);
        int indexToUpdate = -1;
        switch (listName) {
            case "students":
                if (manager.updateStudent(id, values))
                    return 1;
                break;

            case "lecturers":
                if (manager.updateLecturer(id, values))
                    return 1;
                break;

            case "courses":
                if (manager.updateCourse(id, values))
                    return 1;
                break;
        }

        return 0;
    }
}
