package com.example.soenapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterCompletedActivity extends AppCompatActivity {

    TextView name, id, pw;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_completed);

        final Intent intent = getIntent();

        final String nameValue = intent.getExtras().getString("SCHUL_NM") + "님,";
        final String idValue = intent.getExtras().getString("id");
        final String pwValue = intent.getExtras().getString("pw");

        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        pw = findViewById(R.id.pw);

        name.setText(nameValue);
        id.setText(idValue);
        pw.setText(pwValue);

        //TODO mySQL에 회원가입하는 코드 추가

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
