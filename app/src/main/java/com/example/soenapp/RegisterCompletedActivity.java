package com.example.soenapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Objects;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterCompletedActivity extends AppCompatActivity {

    TextView name, id, pw, school, birth, grade, gender;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_completed);

        final Intent intent = getIntent();

        String nameValue = Objects.requireNonNull(intent.getExtras()).getString("name") + "님,";
        String idValue = intent.getExtras().getString("id");
        String pwValue = intent.getExtras().getString("pw");
        String birthValue = intent.getExtras().getString("birth");
        int gradeValue = intent.getExtras().getInt("grade");
        String genderValue = intent.getExtras().getString("gender");
        String schoolNameValue = intent.getExtras().getString("school_name");

        // findViewById
        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        pw = findViewById(R.id.pw);
        school = findViewById(R.id.school);
        birth = findViewById(R.id.birth);
        grade = findViewById(R.id.grade);
        gender = findViewById(R.id.gender);


        // Modify data to view

        // birth
        String[] splitBirths = Objects.requireNonNull(birthValue).split("/");
        String birthYear = splitBirths[0];
        String birthMonth = splitBirths[1];
        String birthDay = splitBirths[2];
        birthValue = birthYear + "년 " + birthMonth + "월 " + birthDay + "일";

        // grade
        int actualGradeValue;
        String schoolType;
        if (1 <= gradeValue && gradeValue <= 6) {
            schoolType = "초등학교";
            actualGradeValue = gradeValue;
        } else if (7 <= gradeValue && gradeValue <= 9) {
            schoolType = "중학교";
            actualGradeValue = gradeValue - 6;
        } else if (10 <= gradeValue && gradeValue <= 12) {
            schoolType = "고등학교";
            actualGradeValue = gradeValue - 9;
        } else {
            schoolType = "?";
            actualGradeValue = 0;
        }
        String stringGradeValue = schoolType + " " + actualGradeValue + "학년";

        // gender
        if (Objects.requireNonNull(genderValue).equals("male")) {
            genderValue = "남성";
        } else if (Objects.requireNonNull(genderValue).equals("female")) {
            genderValue = "여성";
        } else {
            genderValue = "?";
        }


        // setText
        name.setText(nameValue);
        id.setText(idValue);
        pw.setText(pwValue);
        school.setText(schoolNameValue);
        birth.setText(birthValue);
        grade.setText(stringGradeValue);
        gender.setText(genderValue);


        final Intent newIntent = new Intent(getApplicationContext(), LoginActivity.class);

        button = findViewById(R.id.go_to_login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(newIntent);
            }
        });
    }
}
