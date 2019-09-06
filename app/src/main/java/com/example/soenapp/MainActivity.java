package com.example.soenapp;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity {

    ImageView userImage;
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 사용자 사진 원형 테두리로 표시
        userImage = findViewById(R.id.user_photo);
        userImage.setBackground(new ShapeDrawable(new OvalShape()));
        userImage.setClipToOutline(true);

        tabHost = findViewById(R.id.tabHost);
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
    }
}
