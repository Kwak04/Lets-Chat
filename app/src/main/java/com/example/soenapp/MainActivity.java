package com.example.soenapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
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

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TextView userName, schoolName, className, genderName;
    ImageView userImage;
    TabHost tabHost;
    Button goSchool, goClass, goGender;

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

    SharedPreferences getPreferences;

    String genderNameValue;

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

        recyclerView = findViewById(R.id.list_friends);

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
                startActivity(intent);
            }
        });


        // Favorites tab friends list
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

            // temporary
            //TODO 서버 연결 - api 구현
        final ArrayList<FriendsData> friendsDataArrayList = new ArrayList<>();
        friendsDataArrayList.add(new FriendsData("김준일", 0));
        friendsDataArrayList.add(new FriendsData("신일강", 0));
        friendsDataArrayList.add(new FriendsData("최연욱", 0));
        friendsDataArrayList.add(new FriendsData("한승윤", 0));
        friendsDataArrayList.add(new FriendsData("박상범", 0));

        mAdapter = new FavoriteFriendsAdapter(friendsDataArrayList);
        recyclerView.setAdapter(mAdapter);


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
                //손으로 터치한 곳의 좌표를 토대로 해당 Item의 View를 가져옴
                View childView = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());

                //터치한 곳의 View가 RecyclerView 안의 아이템이고 그 아이템의 View가 null이 아니라
                //정확한 Item의 View를 가져왔고, gestureDetector에서 한번만 누르면 true를 넘기게 구현했으니
                //한번만 눌려서 그 값이 true가 넘어왔다면
                if(childView != null && gestureDetector.onTouchEvent(motionEvent)){

                    //현재 터치된 곳의 position을 가져오고
                    int currentPosition = recyclerView.getChildAdapterPosition(childView);

                    //해당 위치의 Data를 가져옴
                    FriendsData currentItemFriend = friendsDataArrayList.get(currentPosition);
                    Toast.makeText(MainActivity.this, currentItemFriend.getName(), Toast.LENGTH_SHORT).show();
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

        recyclerView.addOnItemTouchListener(onItemTouchListener);
    }
}
