package com.example.soenapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText makeName;
    EditText makeID;
    EditText makePW;
    EditText makePW_check;
    Button login;
    static final String URL = "http://13.209.49.105:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        makeName = findViewById(R.id.newName);
        makeID = findViewById(R.id.newID);
        makePW = findViewById(R.id.newPW);
        makePW_check = findViewById(R.id.newPW_check);
        login = findViewById(R.id.next);

        final Intent intent = new Intent(getApplicationContext(), RegisterSchoolActivity.class);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (makePW.getText().toString().equals(makePW_check.getText().toString())) {
                    intent.putExtra("name", makeName.getText().toString());
                    intent.putExtra("id", makeID.getText().toString());
                    intent.putExtra("pw", makePW.getText().toString());

                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
