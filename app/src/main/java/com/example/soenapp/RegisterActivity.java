package com.example.soenapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText makeName, makeID, makePW, makePW_check;
    TextView nameInvalidError, idInvalidError, pwInvalidError, pwIncorrectError;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        makeName = findViewById(R.id.newName);
        makeID = findViewById(R.id.newID);
        makePW = findViewById(R.id.newPW);
        makePW_check = findViewById(R.id.newPW_check);

        nameInvalidError = findViewById(R.id.invalid_name);
        idInvalidError = findViewById(R.id.invalid_id);
        pwInvalidError = findViewById(R.id.invalid_password);
        pwIncorrectError = findViewById(R.id.incorrect_password);

        next = findViewById(R.id.next);

        nameInvalidError.setText("");
        idInvalidError.setText("");
        pwInvalidError.setText("");
        pwIncorrectError.setText("");

        final Intent intent = new Intent(getApplicationContext(), RegisterSchoolActivity.class);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = makeName.getText().toString();
                String id = makeID.getText().toString();
                String pw = makePW.getText().toString();
                String pw_check = makePW_check.getText().toString();


                // 조건 체크

                if (name.equals("") || id.equals("") || pw.equals("") || pw_check.equals("")) {  // 공백이 있는 칸이 있을 경우
                    String toastMessage = "모든 칸에 내용을 입력해 주세요.";
                    Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();

                    // 공백이 있는 칸이 있으면서 '비밀번호'가 '비밀번호 확인'과 다를 경우
                    if (!pw.equals(pw_check)) {  // '비밀번호'가 '비밀번호 확인'과 다를 경우
                        pwIncorrectError.setText(R.string.error_incorrect_password);
                    }
                }
                // 모든 칸이 공백이 아니면서 '비밀번호'가 '비밀번호 확인'과 다를 경우
                else if (!pw.equals(pw_check)) {  // '비밀번호'가 '비밀번호 확인'과 다를 경우
                    pwIncorrectError.setText(R.string.error_incorrect_password);
                }

                // 모든 조건에 만족하는 경우
                else {  // 나머지
                    intent.putExtra("name", name);
                    intent.putExtra("id", id);
                    intent.putExtra("pw", pw);

                    // 액티비티 시작
                    startActivity(intent);
                }
            }
        });
    }
}
