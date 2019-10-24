package com.example.soenapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Objects;

public class RegisterClassActivity extends AppCompatActivity {

    TextView showClass;
    NumberPicker picker;
    Button next;

    int classValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_class);

        // findViewById
        showClass = findViewById(R.id.tv_show);
        picker = findViewById(R.id.np_class);
        next = findViewById(R.id.next);

        // getIntent
        final Intent getIntent = getIntent();
        final String name = Objects.requireNonNull(getIntent.getExtras()).getString("name");
        final String id = getIntent.getExtras().getString("id");
        final String pw = getIntent.getExtras().getString("pw");
        final String birth = getIntent.getExtras().getString("birth");
        final int grade = getIntent.getExtras().getInt("grade");
        final String schoolType = getIntent.getExtras().getString("school_type");
        final int actualGrade = getIntent.getExtras().getInt("actual_grade");
        final String gender = getIntent.getExtras().getString("gender");

        // initialize
        classValue = 1;
        String firstClass = actualGrade + "학년 " + classValue + "반";
        showClass.setText(firstClass);


        // picker
        picker.setMinValue(1);
        picker.setMaxValue(15);
        picker.setWrapSelectorWheel(false);
        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                classValue = newVal;
                String stringNewVal = Integer.toString(newVal);
                String classText = actualGrade + "학년 " + stringNewVal + "반";
                showClass.setText(classText);
            }
        });

        // next button click event
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent newIntent = new Intent(getApplicationContext(), RegisterSchoolActivity.class);
                newIntent.putExtra("name", name);
                newIntent.putExtra("id", id);
                newIntent.putExtra("pw", pw);
                newIntent.putExtra("birth", birth);
                newIntent.putExtra("grade", grade);
                newIntent.putExtra("school_type", schoolType);
                newIntent.putExtra("actual_grade", actualGrade);
                newIntent.putExtra("gender", gender);
                newIntent.putExtra("class", classValue);

                startActivity(newIntent);
            }
        });
    }
}
