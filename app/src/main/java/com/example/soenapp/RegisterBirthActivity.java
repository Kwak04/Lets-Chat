package com.example.soenapp;

import android.content.Intent;
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

public class RegisterBirthActivity extends AppCompatActivity {

    DatePicker datePicker;
    TextView showDate, invalidBirthError;
    Button next;

    int year, month, day, age, grade;
    String date, stringGrade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_birth);

        final String TAG = "RegisterBirthActivity";

        datePicker = findViewById(R.id.date_picker);
        showDate = findViewById(R.id.show_date);
        invalidBirthError = findViewById(R.id.invalid_birth);
        next = findViewById(R.id.next);


        // 초기화
        invalidBirthError.setText("");
        year = 2001;
        month = 0;
        day = 1;

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
                stringGrade = Integer.toString(grade);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent getIntent = getIntent();
                final String name = getIntent.getExtras().getString("name");
                final String id = getIntent.getExtras().getString("id");
                final String pw = getIntent.getExtras().getString("pw");

                final Intent newIntent = new Intent(getApplicationContext(), RegisterGenderActivity.class);
                newIntent.putExtra("name", name);
                newIntent.putExtra("id", id);
                newIntent.putExtra("pw", pw);
                newIntent.putExtra("birth", date);
                newIntent.putExtra("grade", stringGrade);
                startActivity(newIntent);
            }
        });
    }
}
