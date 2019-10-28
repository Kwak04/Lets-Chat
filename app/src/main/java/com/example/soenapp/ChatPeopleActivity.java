package com.example.soenapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ChatPeopleActivity extends AppCompatActivity {

    RecyclerView peopleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_people);

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorGrey));

        peopleList = findViewById(R.id.rv_show_people);
        peopleList.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> items = new ArrayList<>();
        items.add("최연욱");
        items.add("곽현민");
        items.add("박지현");
        items.add("강동우");

        ChatPeopleAdapter chatPeopleAdapter = new ChatPeopleAdapter(items);
        peopleList.setAdapter(chatPeopleAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.dropdown_anim, R.anim.dropdown_disappear_anim);
    }
}
