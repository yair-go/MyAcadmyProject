package com.example.mailo.myacadmyproject.controller;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriPermission;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mailo.myacadmyproject.R;
import com.example.mailo.myacadmyproject.model.backend.AcademyContract;
import com.example.mailo.myacadmyproject.model.datasource.Tools;
import com.example.mailo.myacadmyproject.model.entities.Course;
import com.example.mailo.myacadmyproject.model.entities.Lecturer;

import java.util.List;

public class AddCourseActivity extends Activity implements View.OnClickListener {

    private EditText idEditText;
    private EditText nameEditText;
    private EditText minGradeEditText;
    private CheckBox requiredCheckBox;
    private Spinner lecturerIdSpinner;
    private Button addCourseButton;

    private ListView itemListView;
    private TextView loadingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        findViews();
    }

    @Override
    public void onClick(View v) {
        if (v == addCourseButton) {
            addCourse();
        }
    }

    private void findViews() {
        idEditText = (EditText) findViewById(R.id.idEditText);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        minGradeEditText = (EditText) findViewById(R.id.minGradeEditText);
        requiredCheckBox = (CheckBox) findViewById(R.id.requiredCheckBox);
        lecturerIdSpinner = (Spinner) findViewById(R.id.lecturerIdSpinner);
        addCourseButton = (Button) findViewById(R.id.addCourseButton);
        addCourseButton.setOnClickListener(this);
        lecturerIdSpinner.setEnabled(false);

        itemListView = (ListView) findViewById(R.id.ItemListView);
        loadingTextView = (TextView) findViewById(R.id.loadingTextView);

        final Uri uri = AcademyContract.Course.COURSE_URI;
        updateItemList(uri);


        new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... params) {
                return getContentResolver().query(AcademyContract.Lecturer.LECTURER_URI, null, null, null, null);
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                super.onPostExecute(cursor);
                CursorAdapter adapter = new CursorAdapter(AddCourseActivity.this, cursor) {
                    @Override
                    public View newView(Context context, Cursor cursor, ViewGroup parent) {
                        TextView tv = new TextView(context);
                        tv.setTextSize(15);
                        tv.setTextColor(Color.BLUE);
                        return tv;
                    }

                    @Override
                    public void bindView(View view, Context context, Cursor cursor) {
                        TextView tv = (TextView) view;
                        tv.setText("[" + cursor.getString(0) + "]  " + cursor.getString(1));
                    }
                };
                lecturerIdSpinner.setAdapter(adapter);
                lecturerIdSpinner.setEnabled(true);
            }
        }.execute();
    }

    private void updateItemList(final Uri uri) {

        new AsyncTask<Void, Void, Cursor>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                itemListView.setVisibility(View.INVISIBLE);
                loadingTextView.setVisibility(View.VISIBLE);
            }

            @Override
            protected Cursor doInBackground(Void... params) {
                return getContentResolver().query(uri, null, null, null, null);
                // return null;
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                super.onPostExecute(cursor);
                CursorAdapter adapter = new CursorAdapter(AddCourseActivity.this, cursor) {
                    @Override
                    public View newView(Context context, Cursor cursor, ViewGroup parent) {
                        TextView tv = new TextView(context);

                        return tv;
                    }

                    @Override
                    public void bindView(View view, Context context, Cursor cursor) {
                        TextView tv = (TextView) view;
                        tv.setText("[" + cursor.getString(0) + "]  " + cursor.getString(1));
                    }


                };

                itemListView.setAdapter(adapter);
                itemListView.setVisibility(View.VISIBLE);
                loadingTextView.setVisibility(View.INVISIBLE);
            }
        }.execute();

    }

    private void addCourse() {

        final Uri uri = AcademyContract.Course.COURSE_URI;
        final ContentValues contentValues = new ContentValues();


        try {
            contentValues.put(AcademyContract.Course.ID, Integer.valueOf(this.idEditText.getText().toString()));
        } catch (Exception e) {
        }


        contentValues.put(AcademyContract.Course.NAME, this.nameEditText.getText().toString());
        contentValues.put(AcademyContract.Course.REQUIRED, this.requiredCheckBox.isChecked());
        contentValues.put(AcademyContract.Course.MIN_GRADE, Integer.valueOf(this.minGradeEditText.getText().toString()));

        long lecturerId = ((Cursor) this.lecturerIdSpinner.getSelectedItem()).getLong(0);
        contentValues.put(AcademyContract.Course.LECTURER_ID, lecturerId);


        new AsyncTask<Void, Void, Uri>() {
            @Override
            protected Uri doInBackground(Void... params) {
                return getContentResolver().insert(uri, contentValues);
            }

            @Override
            protected void onPostExecute(Uri uriResult) {
                super.onPostExecute(uriResult);

                long id = ContentUris.parseId(uriResult);
                if (id > 0)
                    Toast.makeText(getBaseContext(), "insert id: " + id, Toast.LENGTH_LONG).show();

                updateItemList(uri);
            }
        }.execute();
    }
}
