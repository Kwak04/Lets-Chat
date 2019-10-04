package com.example.soenapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChatActivity extends AppCompatActivity {

    EditText input;
    TextView who;

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    Chat chat;
    List<Chat> chats;

    SharedPreferences pref = getSharedPreferences("userData", MODE_PRIVATE);

    String myUserKey = pref.getString("user_key", "");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = findViewById(R.id.chat_recycler_view);
        input = findViewById(R.id.chat_input);
        who = findViewById(R.id.chat_who);

        chats = new ArrayList<>();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ChatAdapter(chats, myUserKey);
        recyclerView.setAdapter(mAdapter);

        findViewById(R.id.chat_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Chat chat = new Chat();
                chat.person = "none";
                chat.time = "none";
                chat.user_key = "none";

                chat.text = input.getText().toString();
                chats.add(chat);

                mAdapter = new ChatAdapter(chats, myUserKey);
                recyclerView.setAdapter(mAdapter);
            }
        });
    }
}
