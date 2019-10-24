package com.example.soenapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText editID, editPW;
    String id, pw;
    Button login, register;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(RetrofitService.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RetrofitService retrofitService = retrofit.create(RetrofitService.class);
    HashMap<String, Object> input = new HashMap<>();
    LoginData body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editID = findViewById(R.id.ID);
        editPW = findViewById(R.id.PW);
        login = findViewById(R.id.login_bt);
        register = findViewById(R.id.register_bt);


        sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = editID.getText().toString();
                pw = editPW.getText().toString();

                input.put("id", id);
                input.put("pw", pw);

                retrofitService.postData(input).enqueue(new Callback<LoginData>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginData> call, @NonNull Response<LoginData> response) {
                        if (response.isSuccessful()) {
                            body = response.body();
                            if (Objects.requireNonNull(body).message.equals("success")) {

                                // 서버에서 받은 값들
                                String userTokenValue = body.results[0].user_token;
                                String userKeyValue = body.results[0].user_key;
                                String nameValue = body.results[0].name;
                                String schoolValue = body.results[0].school;
                                String schoolCodeValue = body.results[0].school_code;
                                String birthValue = body.results[0].birth;
                                String schoolTypeValue = body.results[0].school_type;
                                String gradeValue = body.results[0].grade;
                                String actualGradeValue = body.results[0].actual_grade;
                                String genderValue = body.results[0].gender;
                                String schoolClassValue = body.results[0].school_class;

                                // SharedPreferences 저장
                                editor = sharedPreferences.edit();
                                editor.putString("user_token", userTokenValue);
                                editor.putString("user_key", userKeyValue);
                                editor.putString("name", nameValue);
                                editor.putString("school", schoolValue);
                                editor.putString("school_code", schoolCodeValue);
                                editor.putString("birth", birthValue);
                                editor.putString("school_type", schoolTypeValue);
                                editor.putString("grade", gradeValue);
                                editor.putString("actual_grade", actualGradeValue);
                                editor.putString("gender", genderValue);
                                editor.putString("school_class", schoolClassValue);
                                editor.apply();

                                final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);

                            } else if (body.message.equals("fail")) {
                                String toastMessage = "아이디 혹은 비밀번호가 잘못되었습니다.";
                                Toast.makeText(getApplicationContext(),toastMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<LoginData> call, @NonNull Throwable t) {
                        Toast.makeText(getApplicationContext(), "서버를 찾을 수 없음", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        final Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

}
