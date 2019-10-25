package com.example.soenapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Objects;

public class RegisterGenderActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton male, female;
    Button next;

    String gender;

    String TAG = "RegisterGenderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_gender);

        radioGroup = findViewById(R.id.radio_group);
        male = findViewById(R.id.radiobutton1);
        female = findViewById(R.id.radiobutton2);
        next = findViewById(R.id.next);


        // 라디오 버튼 누르기 전 다음 버튼 비활성화
        next.setEnabled(false);


        // 성별 선택
        RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                next.setEnabled(true);
                switch (checkedId) {
                    case R.id.radiobutton1:
                        gender = "male";
                        break;
                    case R.id.radiobutton2:
                        gender = "female";
                        break;
                }
                Log.d(TAG, "listener: gender=" + gender);
            }
        };
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent getIntent = getIntent();
                final String name = Objects.requireNonNull(getIntent.getExtras()).getString("name");
                final String id = getIntent.getExtras().getString("id");
                final String pw = getIntent.getExtras().getString("pw");
                final String birth = getIntent.getExtras().getString("birth");
                final int grade = getIntent.getExtras().getInt("grade");
                final String schoolType = getIntent.getExtras().getString("school_type");
                final int actualGrade = getIntent.getExtras().getInt("actual_grade");

                final Intent newIntent = new Intent(getApplicationContext(), RegisterClassActivity.class);
                newIntent.putExtra("name", name);
                newIntent.putExtra("id", id);
                newIntent.putExtra("pw", pw);
                newIntent.putExtra("birth", birth);
                newIntent.putExtra("grade", grade);
                newIntent.putExtra("school_type", schoolType);
                newIntent.putExtra("actual_grade", actualGrade);
                newIntent.putExtra("gender", gender);
                startActivity(newIntent);
            }
        });
    }
}
