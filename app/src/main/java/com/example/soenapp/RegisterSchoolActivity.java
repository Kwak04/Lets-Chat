package com.example.soenapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterSchoolActivity extends AppCompatActivity {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(RetrofitService.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RetrofitService retrofitService = retrofit.create(RetrofitService.class);
    HashMap<String, Object> input = new HashMap<>();
    SchoolData body;

    private RecyclerView schoolList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    TextWatcher textWatcher;
    EditText editText;
    Button next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_school);

        editText = findViewById(R.id.input);
        schoolList = findViewById(R.id.schoollist);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        schoolList.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        schoolList.setLayoutManager(layoutManager);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                retrofitService.getSchool(s.toString()).enqueue(new Callback<SchoolData>() {
                    @Override
                    public void onResponse(@NonNull Call<SchoolData> call, @NonNull Response<SchoolData> response) {
                        if (response.isSuccessful()) {
                            body = response.body();
                            if (body.message.equals("success")) {

                                // specify an adapter (see also next example)
                                mAdapter = new MyAdapter(body);
                                schoolList.setAdapter(mAdapter);

                                System.out.println(body.toString());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SchoolData> call, @NonNull Throwable t) {

                    }
                });

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final Intent getIntent = getIntent();
//        final Intent newIntent = new Intent(getApplicationContext(), )  // TODO register complete class

        String name = getIntent.getExtras().getString("name");
        String id = getIntent.getExtras().getString("id");
        String pw = getIntent.getExtras().getString("pw");

        Toast.makeText(getApplicationContext(), "이름: " + name + " 아이디: " + id + " 비번: " + pw, Toast.LENGTH_LONG).show();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
