package com.example.mailo.myacadmyproject.controller;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import com.example.mailo.myacadmyproject.R;
import com.example.mailo.myacadmyproject.model.backend.AcademyContract.Student;

public class StudentListActivity extends ListActivity {

    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SimpleCursorAdapter adapter = new SimpleCursorAdapter
                (
                        this,
                        R.layout.item_row,
                        null,
                        new String[]{Student.ID, Student.NAME},
                        new int[]{R.id.itemId, R.id.itemName}
                );

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                cursor = getContentResolver().query(Student.STUDENT_URI, null, null, null, null, null);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.changeCursor(cursor);
            }
        }.execute();




        this.setListAdapter(adapter);


    }
}
