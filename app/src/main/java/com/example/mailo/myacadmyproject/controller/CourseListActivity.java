package com.example.mailo.myacadmyproject.controller;

import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

import com.example.mailo.myacadmyproject.R;
import com.example.mailo.myacadmyproject.model.backend.AcademyContract.Course;

public class CourseListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter
                (
                        this,
                        R.layout.item_row,
                        null,
                        new String[]{Course.ID, Course.NAME},
                        new int[]{R.id.itemId, R.id.itemName}
                );

        new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... params) {
                Cursor cursor = getContentResolver().query(Course.COURSE_URI, null, null, null, null, null);
                return cursor;
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                super.onPostExecute(cursor);
                adapter.changeCursor(cursor);
            }
        }.execute();
        this.setListAdapter(adapter);
    }
}