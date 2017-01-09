package com.example.mailo.myacadmyproject.controller;

import android.Manifest;
import android.app.Activity;
import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mailo.myacadmyproject.R;
import com.example.mailo.myacadmyproject.model.backend.AcademyContract.Course;
import com.example.mailo.myacadmyproject.model.backend.AcademyContract.Student;
import com.example.mailo.myacadmyproject.model.backend.DBManagerFactory;
import com.example.mailo.myacadmyproject.model.backend.DB_manager;
import com.example.mailo.myacadmyproject.model.backend.MyService;
import com.example.mailo.myacadmyproject.model.datasource.MySQL_DBManager;
import com.example.mailo.myacadmyproject.model.datasource.Tools;



public class MainActivity extends Activity implements  View.OnClickListener {


    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private Button addStudentButton;
    private Button addCourseButton;
    private Button addLecturerButton;
    private Button addStudentCourseButton;

    private Button showStudentListButton;
    private Button showCourseListButton;

    private Button sendBroadcastButton;
    private Button startServiceButton;
    private Button getAllContactsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                getContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == addStudentButton) {
            addStudent();
        } else if (v == addLecturerButton) {
            addLecturer();
        } else if (v == addCourseButton) {
            addCourse();
        } else if (v == showStudentListButton) {
            showStudentList();
        } else if (v == showCourseListButton) {
            showCourseList();
        } else if (v == sendBroadcastButton) {
            BroadcastButton();
        } else if (v == startServiceButton) {
            runService();
        } else if (v == getAllContactsButton) {
            getAllContacts();
        }else if (v == addStudentCourseButton) {
            addStudentCourse();
        }
    }



    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-12-07 18:36:19 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        addStudentButton = (Button) findViewById(R.id.addStudentButton);
        addLecturerButton = (Button) findViewById(R.id.addLecturerButton);
        addCourseButton = (Button) findViewById(R.id.addCourseButton);
        addStudentCourseButton = (Button) findViewById(R.id.addStudentCourseButton);

        showStudentListButton = (Button) findViewById(R.id.showStudentListButton);
        showCourseListButton = (Button) findViewById(R.id.showCourseListButton);

        sendBroadcastButton = (Button) findViewById(R.id.sendBroadcastButton);
        startServiceButton = (Button) findViewById(R.id.startServiceButton);
        getAllContactsButton= (Button) findViewById(R.id.getAllContactsButton);

        addStudentButton.setOnClickListener(this);
        addLecturerButton.setOnClickListener(this);
        addCourseButton.setOnClickListener(this);
        addStudentCourseButton.setOnClickListener(this);

        showStudentListButton.setOnClickListener(this);
        showCourseListButton.setOnClickListener(this);

        sendBroadcastButton.setOnClickListener(this);
        startServiceButton.setOnClickListener(this);
        getAllContactsButton.setOnClickListener(this);
    }


    private void addStudent() {
        startActivity(new Intent(this, AddStudentActivity.class));
    }

    private void addLecturer() {
        startActivity(new Intent(this, AddLecturerActivity.class));
    }

    private void addCourse() {
        startActivity(new Intent(this, AddCourseActivity.class));
    }

    private void addStudentCourse() {
        startActivity(new Intent(this,AddStudentCourseActivity.class));
    }


    private void showCourseList() {
        Intent intent = new Intent(this, CourseListActivity.class);
        startActivity(intent);
    }

    private void showStudentList() {
        Intent intent = new Intent(this, StudentListActivity.class);
        startActivity(intent);
    }


    private void runService() {
        //Intent intent = new Intent(this, MyService.class);
      //  startService(intent);

        ComponentName componentName = new ComponentName
                (
                        "com.example.mailo.myacadmyproject",
                        "com.example.mailo.myacadmyproject.model.backend.MyService"
                );

        Intent intent_2 = new Intent();
        intent_2.setComponent(componentName);
        startService(new Intent(intent_2));

        //IllegalArgumentException --->  Service Intent must be explicit
        //   startService(new Intent("com.oshri.academy.SERVICE_UPDATE"));
    }
    private void BroadcastButton() {

        Intent intent = new Intent("com.oshri.academy.Update");
        sendBroadcast(intent);

    }


    private void getContacts() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[]{ContactsContract.Contacts.DISPLAY_NAME};

        Cursor cursor = getContentResolver().query(uri, projection, null, null, null, null);
        String result = "";


        try {
            while (cursor.moveToNext()) {
                result += "\n" + cursor.getString(0);
            }
        } finally {
            cursor.close();
        }

        if (result.equals(""))
            Toast.makeText(this, "Contacts is Empty", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Contacts: " + result, Toast.LENGTH_LONG).show();
    }

    private void getAllContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            getContacts();
        }
    }

}