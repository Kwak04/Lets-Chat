package com.example.soenapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    Button login, register;

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
                                Toast.makeText(getApplicationContext(), "성공!" + " userKey : " + body.results[0].user_key, Toast.LENGTH_SHORT).show();
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

        //채팅 화면 테스트 (임시)

        findViewById(R.id.gotochat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent chat_intent = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(chat_intent);
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
