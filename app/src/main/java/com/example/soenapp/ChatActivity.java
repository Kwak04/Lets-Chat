package com.example.soenapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Chat> chats;
    EditText input;
    TextView who;
    String my_user_key = "1";

    Chat chat;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = findViewById(R.id.chat_recycler_view);
        input = findViewById(R.id.chat_input);
        who = findViewById(R.id.chat_who);

        chats = new ArrayList<>();

        int a;

        for (int i = 0; i < 10 ; i++){

            chat = new Chat();
            chat.person = String.valueOf(i);
            chat.text = String.valueOf(i);
            chat.time = String.valueOf(i);

            a = random.nextInt(3);
            chat.user_key = String.valueOf(a);

            System.out.println(a);

            chats.add(chat);
        }
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ChatMyAdapter(chats, my_user_key);
        recyclerView.setAdapter(mAdapter);

        findViewById(R.id.chat_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Chat chat = new Chat();

                chat.person = "test";
                chat.time = "test";
                chat.user_key = "1";

                chat.text = input.getText().toString();
                chats.add(chat);

                for (int i = 0 ; i < chats.size(); i++) {
                    System.out.println(chats.get(i).toString());
                }
                mAdapter = new ChatMyAdapter(chats, my_user_key);
                recyclerView.setAdapter(mAdapter);

            }
        });
    }
}
