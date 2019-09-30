package com.example.soenapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText editID, editPW;
    String id, pw;
    Button login, register, test;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(RetrofitService.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RetrofitService retrofitService = retrofit.create(RetrofitService.class);
    HashMap<String, Object> input = new HashMap<>();
    LoginData body;

    // temporary
    // TODO remove this after the test
    TextView myUserKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editID = findViewById(R.id.ID);
        editPW = findViewById(R.id.PW);
        login = findViewById(R.id.login_bt);
        register = findViewById(R.id.register_bt);

        test = findViewById(R.id.btn_test_screen);

        sharedPreferences = getSharedPreferences("appData", MODE_PRIVATE);

        // temporary
//        editor = sharedPreferences.edit();
//        editor.putString("text", "");
//        editor.apply();

//        myUserKey = findViewById(R.id.my_user_key);
//        myUserKey.setText(sharedPreferences.getString("text", ""));

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
                            if (body.message.equals("success")) {
                                String toastMessage = "userKey: " + body.results[0].user_key + "\n환영합니다 " + body.results[0].name + "님!";
                                Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                                editor = sharedPreferences.edit();
                                editor.putString("user_key", body.results[0].user_key);
                                editor.putString("name", body.results[0].name);
                                editor.apply();
                            } else if (body.message.equals("fail")) {
                                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
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

        // 테스트 화면 버튼
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                startActivity(intent);
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
