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
                String name = makeName.getText().toString();
                String id = makeID.getText().toString();
                String pw = makePW.getText().toString();
                String pw_check = makePW_check.getText().toString();

                if (!(name.equals("")) || !(id.equals("")) || !(pw.equals("")) || !(pw_check.equals(""))) {
                    if (pw.equals(pw_check)) {
                        intent.putExtra("name", name);
                        intent.putExtra("id", id);
                        intent.putExtra("pw", pw);

                        startActivity(intent);
                        
                    } else {
                        Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
