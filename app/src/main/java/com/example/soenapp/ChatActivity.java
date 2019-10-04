package com.example.soenapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatActivity extends AppCompatActivity {

    EditText input;
    TextView who;
    Button send;

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    Chat chat;
    List<Chat> chats;

    SharedPreferences pref;

    Socket socket;

    final String TAG = "ChatActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.chat_recycler_view);
        input = findViewById(R.id.chat_input);
        who = findViewById(R.id.chat_who);
        send = findViewById(R.id.chat_send);


        // Socket Communication
        Emitter.Listener onConnect = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                socket.emit("clientMessage", "hi");
            }
        };

        Emitter.Listener onMessageReceived = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    JSONObject receivedData = (JSONObject) args[0];
                    Log.d(TAG, receivedData.getString("msg"));
                    Log.d(TAG, receivedData.getString("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        try {
            String url = RetrofitService.URL;
            socket = IO.socket(url);
            socket.connect();
            socket.on(Socket.EVENT_CONNECT, onConnect);
            socket.on("serverMessage", onMessageReceived);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }



        // SharedPreferences
        pref = getSharedPreferences("userData", MODE_PRIVATE);
        final String myUserKey = pref.getString("user_key", "");

        chats = new ArrayList<>();

        // RecyclerView
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ChatAdapter(chats, myUserKey);
        recyclerView.setAdapter(mAdapter);

        send.setOnClickListener(new View.OnClickListener() {
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
