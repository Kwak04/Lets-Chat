package com.example.soenapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatActivity extends AppCompatActivity {

    EditText input;
    TextView roomName;
    Button send;
    ImageButton showPeople;

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    List<ChatData> chats;

    SharedPreferences pref;

    Socket socket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // findViewById

        roomName = findViewById(R.id.room_name);
        showPeople = findViewById(R.id.btn_show_people);

        recyclerView = findViewById(R.id.chat_recycler_view);
        input = findViewById(R.id.chat_input);
        send = findViewById(R.id.chat_send);


        // Room information
        final Intent intent = getIntent();
        final String roomNameValue = Objects.requireNonNull(intent.getExtras()).getString("roomName");
        final String roomKey = intent.getExtras().getString("roomKey");
        final boolean isPersonal = intent.getExtras().getBoolean("isPersonal");

        // SharedPreferences
        pref = getSharedPreferences("userData", MODE_PRIVATE);
        final String myUserKey = pref.getString("user_key", "");
        final String myName = pref.getString("name", "");

        chats = new ArrayList<>();


        // Change status bar's color
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorGrey));

        // View room's name
        roomName.setText(roomNameValue);

        // check room's type and enable or disable people button
        if (isPersonal) {
            showPeople.setImageResource(0);
            showPeople.setEnabled(false);
        }

        // Socket Communication

        // 연결되었을 때
        Emitter.Listener onConnect = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                socket.emit("requestChat", roomKey);
            }
        };

        // 데이터베이스에 저장된 메시지들을 받았을 때
        Emitter.Listener onSavedMessagesReceived = new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                final String TAG = "onSavedMessagesReceived";
                try {
                    JSONObject receivedData = (JSONObject) args[0];
                    Log.d(TAG, "receivedData: " + receivedData);
                    String seq = receivedData.getString("seq");
                    String userKey = receivedData.getString("user_key");
                    String name = receivedData.getString("user_name");
                    String time = receivedData.getString("time");
                    String text = receivedData.getString("text");

                    Log.d(TAG, "userKey: " + userKey);
                    Log.d(TAG, "seq: " + seq);

                    ChatData chat = new ChatData();
                    chat.person = name;
                    chat.time = time;
                    chat.user_key = userKey;
                    chat.text = text;
                    chat.seq = seq;
                    chats.add(chat);

                    Collections.sort(chats, new Comparator<ChatData>() {
                        @Override
                        public int compare(ChatData lhs, ChatData rhs) {
                            return Integer.compare(lhs.seq.compareTo(rhs.seq), 0);
                        }
                    });

                    mAdapter = new ChatAdapter(chats, myUserKey);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(mAdapter);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        // 'chatMessage'를 받았을 때
        Emitter.Listener onChatMessageReceived = new Emitter.Listener() {
            final String TAG = "onChatMessageReceived";
            @Override
            public void call(Object... args) {
                try {
                    Log.d(TAG, "call: CHAT MESSAGE RECEIVED!!!!!!!!");
                    JSONObject receivedData = (JSONObject) args[0];
                    String seq = receivedData.getString("seq");
                    String name = receivedData.getString("user_name");
                    String time = receivedData.getString("time");
                    String userKey = receivedData.getString("user_key");
                    String text = receivedData.getString("text");

                    Log.d(TAG, "seq: " + seq);
                    Log.d(TAG, "name: " + name);
                    Log.d(TAG, "time: " + time);
                    Log.d(TAG, "text: " + text);

                    if (receivedData.getString("room_key").equals(roomKey)) {
                        ChatData chat = new ChatData();
                        chat.person = name;
                        chat.time = time;
                        chat.user_key = userKey;
                        chat.text = text;
                        chat.seq = seq;
                        chats.add(chat);

                        Collections.sort(chats, new Comparator<ChatData>() {
                            @Override
                            public int compare(ChatData lhs, ChatData rhs) {
                                return Integer.compare(lhs.seq.compareTo(rhs.seq), 0);
                            }
                        });

                        mAdapter = new ChatAdapter(chats, myUserKey);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setAdapter(mAdapter);
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        // 'checkSaveChat'을 받았을 때
        Emitter.Listener onCheckSaveChatReceived = new Emitter.Listener() {
            final String TAG = "onCheckSaveChatReceived";
            @Override
            public void call(Object... args) {
                try {
                    JSONObject receivedData = (JSONObject) args[0];
                    Log.d(TAG, receivedData.getString("message"));

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
            socket.on("savedMessages", onSavedMessagesReceived);
            socket.on("chatMessage", onChatMessageReceived);
            socket.on("checkSaveChat", onCheckSaveChatReceived);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        // RecyclerView
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ChatAdapter(chats, myUserKey);
        recyclerView.setAdapter(mAdapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(roomKey == null){
                    return;
                }


                Date time = new Date();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatActual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatDisplay = new SimpleDateFormat("HH:mm");

                String timeActual = formatActual.format(time.getTime());
                String timeDisplay = formatDisplay.format(time.getTime());

                String text = input.getText().toString();
                boolean isEmpty;
                isEmpty = text.equals("");

                if (isEmpty) {
                    Toast.makeText(getApplicationContext(), "메시지를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // Put chat data to the database

                    JSONObject chatInfo = new JSONObject();
                    try {
                        chatInfo.put("user_key", myUserKey);
                        chatInfo.put("user_name", myName);
                        chatInfo.put("time", timeDisplay);
                        chatInfo.put("time_detail", timeActual);
                        chatInfo.put("text", text);
                        chatInfo.put("room_key", roomKey);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    socket.emit("saveChat", chatInfo);

                    mAdapter = new ChatAdapter(chats, myUserKey);
                    recyclerView.setAdapter(mAdapter);

                    // 입력된 내용 지우기
                    input.setText(null);
                }
            }
        });


        // 사람 목록 보기
        showPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(ChatActivity.this, ChatPeopleActivity.class);
                intent.putExtra("roomKey", roomKey);
                startActivity(intent);
                overridePendingTransition(R.anim.riseup_anim, R.anim.riseup_disappear_anim);
            }
        });
    }
}
