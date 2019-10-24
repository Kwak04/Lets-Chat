package com.example.soenapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TextView;

public class RegisterClassActivity extends AppCompatActivity {

    TextView showClass;
    NumberPicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_class);

        // findViewById
        showClass = findViewById(R.id.tv_show);
        picker = findViewById(R.id.np_class);

        // getIntent
        final Intent getIntent = getIntent();
        final String name = getIntent.getExtras().getString("name");
        final String id = getIntent.getExtras().getString("id");
        final String pw = getIntent.getExtras().getString("pw");
        final String birth = getIntent.getExtras().getString("birth");
        final String grade = getIntent.getExtras().getString("grade");
        final String gender = getIntent.getExtras().getString("gender");


        // picker
        picker.setMinValue(1);
        picker.setMaxValue(15);
        picker.setWrapSelectorWheel(false);
        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String stringNewVal = Integer.toString(newVal);
                showClass.setText(stringNewVal);
            }
        });
    }
}
