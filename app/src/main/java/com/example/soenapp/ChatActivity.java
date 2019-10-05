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

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    ChatData chat;
    List<ChatData> chats;

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
                socket.emit("clientMessage", "Connected!");
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
        final String myName = pref.getString("name", "");

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

                String text = input.getText().toString();
                Date time = new Date();
                SimpleDateFormat formatActual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat formatDisplay = new SimpleDateFormat("HH:mm");

                String timeActual = formatActual.format(time.getTime());
                String timeDisplay = formatDisplay.format(time.getTime());

                ChatData chat = new ChatData();
                chat.person = myName;
                chat.time = timeDisplay;
                chat.user_key = "none";

                chat.text = text;
                chats.add(chat);

                socket.emit("clientMessage", text);

                mAdapter = new ChatAdapter(chats, myUserKey);
                recyclerView.setAdapter(mAdapter);
            }
        });
    }
}
