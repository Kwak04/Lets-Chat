package com.example.soenapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatPeopleActivity extends AppCompatActivity {

    final String TAG = "ChatPeopleActivity";

    RecyclerView peopleList;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(RetrofitService.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RetrofitService retrofitService = retrofit.create(RetrofitService.class);
    ChatPeopleData peopleBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_people);

        // findViewById
        peopleList = findViewById(R.id.rv_show_people);

        // setLayoutManager
        peopleList.setLayoutManager(new LinearLayoutManager(this));

        // Set status bar's color
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorGrey));

        // getIntent
        final Intent getIntent = getIntent();
        final String roomKey = Objects.requireNonNull(getIntent.getExtras()).getString("roomKey");
        Log.d(TAG, "roomKey: " + roomKey);


        // People list

        retrofitService.showChatPeople(roomKey).enqueue(new Callback<ChatPeopleData>() {
            @Override
            public void onResponse(@NonNull Call<ChatPeopleData> call, @NonNull Response<ChatPeopleData> response) {
                if (response.isSuccessful()) {
                    peopleBody = response.body();
                    if (Objects.requireNonNull(peopleBody).message.equals("success")) {
                        ChatPeopleAdapter chatPeopleAdapter = new ChatPeopleAdapter(peopleBody);
                        peopleList.setAdapter(chatPeopleAdapter);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ChatPeopleData> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "서버에 접속할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.dropdown_anim, R.anim.dropdown_disappear_anim);
    }
}
