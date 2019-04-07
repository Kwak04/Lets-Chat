package com.example.soenapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterSchoolActivity extends AppCompatActivity {

    TextWatcher textWatcher;

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_school);

        editText = findViewById(R.id.edittext);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Intent getIntent = getIntent();

        String name = getIntent.getExtras().getString("name");
        String id = getIntent.getExtras().getString("id");
        String pw = getIntent.getExtras().getString("pw");

        Toast.makeText(getApplicationContext(), "이름: " + name + " 아이디: " + id + " 비번: " + pw, Toast.LENGTH_LONG).show();
    }
}
