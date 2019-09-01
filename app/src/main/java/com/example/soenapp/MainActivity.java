package com.example.soenapp;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView userImage;
    GradientDrawable rounding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userImage = findViewById(R.id.user_photo);

        rounding = (GradientDrawable) getApplicationContext().getDrawable(R.drawable.photo_rounding);
        userImage.setBackground(rounding);
        userImage.setClipToOutline(true);
    }
}
