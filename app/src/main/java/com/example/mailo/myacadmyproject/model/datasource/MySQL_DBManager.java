package com.example.mailo.myacadmyproject.model.datasource;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.util.Log;

import com.example.mailo.myacadmyproject.model.backend.AcademyContract;
import com.example.mailo.myacadmyproject.model.backend.DB_manager;
import com.example.mailo.myacadmyproject.model.entities.Course;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by mailo on 15/12/2016.
 */

public class MySQL_DBManager implements DB_manager {

    private final String UserName="ygoldsht";
    //private final String WEB_URL = "http://"+UserName+".vlab.jct.ac.il/academy/";         //   " שם ה URI שלך";  // "https://oshric.vlab.jct.ac.il/";

    private final String WEB_URL = "https://bookstore-yairjct.c9users.io/";
    private boolean updateFlag = false;


    public void printLog(String message)
    {
        Log.d(this.getClass().getName(),"\n"+message);
    }
    public void printLog(Exception message)
    {
        Log.d(this.getClass().getName(),"Exception-->\n"+message);
    }

@Override
public long addStudent(ContentValues values) {
    try {
        String result = PHPtools.POST(WEB_URL + "/addStudent.php", values);
        long id = Long.parseLong(result);
        if (id > 0)
            SetUpdate();
        printLog("addStudent:\n" + result);
        return id;
    } catch (IOException e) {
        printLog("addStudent Exception:\n" + e);
        return -1;
    }
}

    @Override
    public long addLecturer(ContentValues values) {
        try {
            String result = PHPtools.POST(WEB_URL + "/addLecturer.php", values);
            long id = Long.parseLong(result);
            if (id > 0)
                SetUpdate();
            printLog("addLecturer:\n" +result);
            return id;
        } catch (IOException e) {
            printLog("addLecturer:\n" +e);
        }
        return -1;
    }

    @Override
    public long addCourse(ContentValues values) {
        try {
            String result = PHPtools.POST(WEB_URL + "/addCourse.php", values);
            long id = Long.parseLong(result);
            if (id > 0)
                SetUpdate();
            printLog("addCourse:\n" +result);
            return id;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }


@Override
public Cursor getStudents() {
    try {
        MatrixCursor matrixCursor = new MatrixCursor(new String[]
                {
                        AcademyContract.Student.ID,
                        AcademyContract.Student.NAME,
                        AcademyContract.Student.PHONE,
                        AcademyContract.Student.YEAR
                });
        String str = PHPtools.GET(WEB_URL + "/students.php");
        JSONArray array = new JSONObject(str).getJSONArray("students");


        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = null;

            jsonObject = array.getJSONObject(i);

            matrixCursor.addRow(new Object[]{
                    jsonObject.getInt(AcademyContract.Student.ID),
                    jsonObject.getString(AcademyContract.Student.NAME),
                    jsonObject.getString(AcademyContract.Student.PHONE),
                    jsonObject.getString(AcademyContract.Student.YEAR)
            });
        }
        return matrixCursor;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

    @Override
    public Cursor getLecturer() {
        try {
            MatrixCursor matrixCursor = new MatrixCursor(new String[]
                    {
                            AcademyContract.Lecturer.ID,
                            AcademyContract.Lecturer.NAME,
                            AcademyContract.Lecturer.PHONE,
                            AcademyContract.Lecturer.SENIORITY
                    });
            String str = PHPtools.GET(WEB_URL + "/lecturers.php");
            JSONArray array = new JSONObject(str).getJSONArray("lecturers");


            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = null;

                jsonObject = array.getJSONObject(i);

                matrixCursor.addRow(new Object[]{
                        jsonObject.getInt(AcademyContract.Lecturer.ID),
                        jsonObject.getString(AcademyContract.Lecturer.NAME),
                        jsonObject.getString(AcademyContract.Lecturer.PHONE),
                        jsonObject.getInt(AcademyContract.Lecturer.SENIORITY)
                });
            }
            return matrixCursor;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Cursor getCourses() {
        String[] columns = new String[]
                {
                        AcademyContract.Course.ID,
                        AcademyContract.Course.NAME,
                        AcademyContract.Course.MIN_GRADE,
                        AcademyContract.Course.REQUIRED,
                        AcademyContract.Course.LECTURER_ID

                };

        MatrixCursor matrixCursor = new MatrixCursor(columns);
        try {
            String str = PHPtools.GET(WEB_URL + "/courses.php");
            JSONArray array = new JSONObject(str).getJSONArray("courses");


            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = null;

                jsonObject = array.getJSONObject(i);

                matrixCursor.addRow(new Object[]{
                        jsonObject.getInt(AcademyContract.Course.ID),
                        jsonObject.getString(AcademyContract.Course.NAME),
                        jsonObject.getInt(AcademyContract.Course.MIN_GRADE),
                        jsonObject.getBoolean(AcademyContract.Course.REQUIRED),
                        jsonObject.getInt(AcademyContract.Course.LECTURER_ID)
                });
            }
            return matrixCursor;
        } catch (Exception e) {
            return null;
        }


    }


    @Override
    public boolean removeStudent(long id) {
        return false;
    }

    @Override
    public boolean removeCourse(long id) {
        return false;
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
    public long addStudentCourse(ContentValues values) {
        try {
            String result = PHPtools.POST(WEB_URL + "/addStudentCourse.php", values);
            long id = Long.parseLong(result);
            if (id > 0)
                SetUpdate();
            printLog("addStudentCourse:\n" +result);
            return id;
        } catch (IOException e) {
            printLog("addStudentCourse:\n" +e);
        }
        return -1;
    }

    @Override
    public boolean updateStudent(long id, ContentValues values) {
        return false;
    }

    @Override
    public boolean updateCourse(long id, ContentValues values) {
        return false;
    }


    private void SetUpdate()
    {
        updateFlag = true;
    }

    @Override
    public boolean isUpdatet() {
        if(updateFlag)
        {
            updateFlag=false;
            return  true;
        }

        return  false;
    }


}