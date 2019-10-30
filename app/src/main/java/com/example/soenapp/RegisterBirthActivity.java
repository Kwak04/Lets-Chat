package com.example.soenapp;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

public class RegisterBirthActivity extends AppCompatActivity {

    DatePicker datePicker;
    TextView showDate, invalidBirthError;
    Button next;

    int year, month, day, age, grade, actualGrade;
    String date, schoolType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_birth);

        final String TAG = "RegisterBirthActivity";

        datePicker = findViewById(R.id.date_picker);
        showDate = findViewById(R.id.show_date);
        invalidBirthError = findViewById(R.id.invalid_birth);
        next = findViewById(R.id.next);


        getWindow().setStatusBarColor(getResources().getColor(R.color.colorWhite));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        // initialize
        invalidBirthError.setText("");
        year = 2001;
        month = 0;
        day = 1;
        actualGrade = 3;
        grade = 12;
        schoolType = "high";
        date = "2001/1/1";

        // datePicker: disable keyboard input
        datePicker.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 생년월일 표시
                date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                showDate.setText(date);

                // 생년월일 유효성 검사
                Calendar calendar = new GregorianCalendar(Locale.KOREA);
                int nowYear = calendar.get(Calendar.YEAR);
                Log.d(TAG, "nowYear: " + nowYear);
                age = nowYear - year + 1;
                Log.d(TAG, "onDateChanged: age = " + age);
                final int MIN_AGE = 8;
                final int MAX_AGE = 19;

                if (age >= MIN_AGE && age <= MAX_AGE) {
                    invalidBirthError.setText("");  // 에러 메시지 숨기기
                    next.setEnabled(true);  // 다음 버튼 활성화
                } else {
                    invalidBirthError.setText(R.string.error_register_birth_invalid_birth);  // 에러 메시지 표시
                    next.setEnabled(false);  // 다음 버튼 비활성화
                }

                // grade
                grade = age - 8 + 1;  // 초1: 1학년  고3: 12학년

                // schoolType, actualGrade
                if (1 <= grade && grade <= 6) {
                    schoolType = "elementary";
                    actualGrade = grade;
                } else if (7 <= grade && grade <= 9) {
                    schoolType = "middle";
                    actualGrade = grade - 6;
                } else if (10 <= grade && grade <= 12) {
                    schoolType = "high";
                    actualGrade = grade - 9;
                } else {
                    schoolType = "?";
                    actualGrade = 0;
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent getIntent = getIntent();
                final String name = Objects.requireNonNull(getIntent.getExtras()).getString("name");
                final String id = getIntent.getExtras().getString("id");
                final String pw = getIntent.getExtras().getString("pw");

                final Intent newIntent = new Intent(getApplicationContext(), RegisterGenderActivity.class);
                newIntent.putExtra("name", name);
                newIntent.putExtra("id", id);
                newIntent.putExtra("pw", pw);
                newIntent.putExtra("birth", date);
                newIntent.putExtra("grade", grade);
                newIntent.putExtra("school_type", schoolType);
                newIntent.putExtra("actual_grade", actualGrade);

                startActivity(newIntent);
            }
        });
    }
}
