package com.example.soenapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<Chat> chats;

    EditText input;
    Chat chat = new Chat();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = findViewById(R.id.chat_recycler_view);
        input = findViewById(R.id.chat_input);

        chats = new ArrayList<>();


        for (int i = 0; i <10 ; i++){
            chat.person = String.valueOf(i);
            chat.text = String.valueOf(i);
            chat.time = String.valueOf(i);

            chats.add(chat);
        }
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ChatMyAdapter(chats);
        recyclerView.setAdapter(mAdapter);

        findViewById(R.id.chat_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Chat chat = new Chat();

                chat.person = "test";
                chat.time = "test";

                chat.text = input.getText().toString();
                chats.add(chat);

                for (int i = 0 ; i < chats.size(); i++) {
                    System.out.println(chats.get(i).toString());
                }
                mAdapter = new ChatMyAdapter(chats);
                recyclerView.setAdapter(mAdapter);

            }
        });
    }
}
