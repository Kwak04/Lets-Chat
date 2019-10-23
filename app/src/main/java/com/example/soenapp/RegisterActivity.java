package com.example.soenapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    EditText makeName, makeID, makePW, makePW_check;
    TextView nameInvalidError, idInvalidError, pwInvalidError, pwIncorrectError;
    Button next;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(RetrofitService.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RetrofitService retrofitService = retrofit.create(RetrofitService.class);
    SimpleMessageData resultBody;
    boolean isIdOverlapping = true;

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

        final Intent intent = new Intent(getApplicationContext(), RegisterBirthActivity.class);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = makeName.getText().toString();
                final String id = makeID.getText().toString();
                final String pw = makePW.getText().toString();
                final String pw_check = makePW_check.getText().toString();


                // 조건 체크

                // 아이디 중복 확인
                retrofitService.checkID(id).enqueue(new Callback<SimpleMessageData>() {
                    @Override
                    public void onResponse(@NonNull Call<SimpleMessageData> call, @NonNull Response<SimpleMessageData> response) {
                        if (response.isSuccessful()) {
                            resultBody = response.body();
                            String message = resultBody.message;
                            if (message.equals("overlapping")) {  // 이미 데이터베이스에 같은 아이디가 있을 경우
                                idInvalidError.setText(R.string.error_overlapping_id);
                                isIdOverlapping = true;
                            } else {  // 없을 경우
                                idInvalidError.setText("");
                                isIdOverlapping = false;

                                boolean check1, check2, check3, check4, check5;
                                check1 = !(name.equals("") || id.equals("") || pw.equals("") || pw_check.equals(""));
                                check2 = pw.equals(pw_check);
                                check3 = Pattern.matches("^[가-힣]{1,10}$", name);
                                check4 = Pattern.matches("^[a-z0-9]{3,20}$", id);
                                check5 = Pattern.matches("^[a-z0-9]{6,20}$", pw);

                                // 공백이 있는 칸이 있을 경우
                                if (!check1) {
                                    String toastMessage = "모든 칸에 내용을 입력해 주세요.";
                                    Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                                }
                                // '비밀번호'가 '비밀번호 확인'과 다를 경우
                                if (!check2) {
                                    pwIncorrectError.setText(R.string.error_incorrect_password);
                                } else {  // 같을 경우
                                    pwIncorrectError.setText("");
                                }
                                // '이름'이 한글이 아니거나 글자 수가 10을 넘어갈 경우
                                if (!check3) {
                                    nameInvalidError.setText(R.string.error_invalid_name);
                                } else {  // 맞을 경우
                                    nameInvalidError.setText("");
                                }
                                // '아이디'가 영문과 숫자로 이루어지지 않았거나 글자 수가 3자리~20자리 범위에 없을 경우
                                if (!check4) {
                                    idInvalidError.setText(R.string.error_invalid_id);
                                } else {  // 맞을 경우
                                    idInvalidError.setText("");
                                }
                                // '비밀번호'가 영문과 숫자로 이루어지지 않았거나 글자 수가 6자리~20자리 범위에 없을 경우
                                if (!check5) {
                                    pwInvalidError.setText(R.string.error_invalid_password);
                                } else {
                                    pwInvalidError.setText("");
                                }
                                // 모든 조건에 만족하는 경우
                                if (check1 && check2 && check3 && check4 && check5) {
                                    intent.putExtra("name", name);
                                    intent.putExtra("id", id);
                                    intent.putExtra("pw", pw);
                                    startActivity(intent);  // 액티비티 시작
                                }
                            }
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<SimpleMessageData> call, @NonNull Throwable t) {
                        Toast.makeText(RegisterActivity.this, "데이터 전송에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
