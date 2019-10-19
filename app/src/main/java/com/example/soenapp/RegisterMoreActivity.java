package com.example.soenapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class RegisterMoreActivity extends AppCompatActivity {

    DatePicker datePicker;
    TextView showDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_more);

        String TAG = "RegisterMoreActivity";

        datePicker = findViewById(R.id.date_picker);
        showDate = findViewById(R.id.show_date);


        int year = 2012, month = 1, day = 1;
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = year + "/" + monthOfYear + "/" + dayOfMonth;
                showDate.setText(date);
            }
        });
    }
}
