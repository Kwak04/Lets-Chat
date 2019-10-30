package com.example.soenapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView userName, schoolName, className, genderName;
    ImageView userImage;
    TabHost tabHost;
    Button goSchool, goClass, goGender;

    RecyclerView favoriteFriendsList;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    SharedPreferences getPreferences;

    String genderNameValue;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(RetrofitService.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RetrofitService retrofitService = retrofit.create(RetrofitService.class);
    ChatPeopleData peopleBody;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userName = findViewById(R.id.user_name);
        userImage = findViewById(R.id.user_photo);

        tabHost = findViewById(R.id.tabHost);

        schoolName = findViewById(R.id.my_school);
        className = findViewById(R.id.my_class);
        genderName = findViewById(R.id.my_gender);

        goSchool = findViewById(R.id.btn_go_school);
        goClass = findViewById(R.id.btn_go_class);
        goGender = findViewById(R.id.btn_go_gender);

        favoriteFriendsList = findViewById(R.id.list_friends);

        getPreferences = getSharedPreferences("userData", MODE_PRIVATE);


        // Title bar

        // 사용자 이름 표시
        String userNameValue = getPreferences.getString("name", "user");
        userName.setText(userNameValue);

        // 사용자 사진 원형 테두리로 표시
        // 기본값이 Vector 이미지이므로 일단 주석 처리
//        userImage.setBackground(new ShapeDrawable(new OvalShape()));
//        userImage.setClipToOutline(true);


        // Tabs
        tabHost.setup();

        // First Tab
        TabHost.TabSpec ts1 = tabHost.newTabSpec("TabSpec1");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("내 클럽");
        tabHost.addTab(ts1);

        // Second Tab
        TabHost.TabSpec ts2 = tabHost.newTabSpec("TabSpec2");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("즐겨찾기");
        tabHost.addTab(ts2);


        // Setup

        // user's school name
        final String schoolRoomNameValue = getPreferences.getString("school", "");
        schoolName.setText(schoolRoomNameValue);

        // user's class name
        final String classValue = getPreferences.getString("school_class", "");
        final String actualGradeValue = getPreferences.getString("actual_grade", "");
        final String classRoomNameValue = actualGradeValue + "학년 " + classValue + "반";
        className.setText(classRoomNameValue);

        // user's gender
        final String genderValue = getPreferences.getString("gender", "");
        if (Objects.equals(genderValue, "male")) {
            genderNameValue = "남자";
        } else if (Objects.equals(genderValue, "female")) {
            genderNameValue = "여자";
        }
        final String genderRoomNameValue = actualGradeValue + "학년 " + classValue + "반 " + genderNameValue;
        genderName.setText(genderRoomNameValue);

        // School chat button
        goSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                String roomKeyValue = getPreferences.getString("school_code", "");
                intent.putExtra("roomName", schoolRoomNameValue);
                intent.putExtra("roomKey", roomKeyValue);
                intent.putExtra("isPersonal", false);
                startActivity(intent);
            }
        });

        // Class chat button
        goClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                String schoolCodeValue = getPreferences.getString("school_code", "");
                String roomKeyValue = schoolCodeValue + "-" + actualGradeValue + "-" + classValue;
                intent.putExtra("roomName", classRoomNameValue);
                intent.putExtra("roomKey", roomKeyValue);
                intent.putExtra("isPersonal", false);
                startActivity(intent);
            }
        });

        // Gender chat button
        goGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                String schoolCodeValue = getPreferences.getString("school_code", "");
                String roomKeyValue = schoolCodeValue + "-" + actualGradeValue + "-" + classValue + "-" + genderValue;
                intent.putExtra("roomName", genderRoomNameValue);
                intent.putExtra("roomKey", roomKeyValue);
                intent.putExtra("isPersonal", false);
                startActivity(intent);
            }
        });


        // Favorites tab friends list
        favoriteFriendsList.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        favoriteFriendsList.setLayoutManager(layoutManager);

        final String userKey = getPreferences.getString("user_key", "");

        retrofitService.checkFavorite(userKey).enqueue(new Callback<ChatPeopleData>() {
            @Override
            public void onResponse(@NonNull Call<ChatPeopleData> call, @NonNull Response<ChatPeopleData> response) {
                if (response.isSuccessful()) {
                    peopleBody = response.body();
                    if (Objects.requireNonNull(peopleBody).message.equals("success")) {
                        FavoriteFriendsAdapter chatPeopleAdapter = new FavoriteFriendsAdapter(peopleBody);
                        favoriteFriendsList.setAdapter(chatPeopleAdapter);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ChatPeopleData> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "서버에 접속할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });


        // Friends list click event
        final GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        RecyclerView.OnItemTouchListener onItemTouchListener = new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

                View childView = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());

                if(childView != null && gestureDetector.onTouchEvent(motionEvent)){

                    int currentPosition = recyclerView.getChildAdapterPosition(childView);

                    ChatPeopleData.Result currentItemFriend = peopleBody.results[currentPosition];

                    int intUserKey = Integer.parseInt(Objects.requireNonNull(userKey));
                    int intFriendsUserKey = Integer.parseInt(currentItemFriend.user_key);

                    final Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                    String roomKeyValue = Math.min(intUserKey, intFriendsUserKey) + "-" + Math.max(intUserKey, intFriendsUserKey);
                    intent.putExtra("roomName", currentItemFriend.name);
                    intent.putExtra("roomKey", roomKeyValue);
                    intent.putExtra("isPersonal", true);
                    startActivity(intent);

                    return true;
                }

                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        };

        favoriteFriendsList.addOnItemTouchListener(onItemTouchListener);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        final String userKey = getPreferences.getString("user_key", "");

        retrofitService.checkFavorite(userKey).enqueue(new Callback<ChatPeopleData>() {
            @Override
            public void onResponse(@NonNull Call<ChatPeopleData> call, @NonNull Response<ChatPeopleData> response) {
                if (response.isSuccessful()) {
                    peopleBody = response.body();
                    if (Objects.requireNonNull(peopleBody).message.equals("success")) {
                        FavoriteFriendsAdapter chatPeopleAdapter = new FavoriteFriendsAdapter(peopleBody);
                        favoriteFriendsList.setAdapter(chatPeopleAdapter);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ChatPeopleData> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "서버에 접속할 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
