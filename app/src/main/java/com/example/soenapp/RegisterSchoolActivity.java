package com.example.soenapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

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

        next = findViewById(R.id.next);

        editText = findViewById(R.id.input);
        schoolList = findViewById(R.id.school_list);

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

            }

            Timer timer = new Timer();
            final int DELAY = 200;

            @Override
            public void afterTextChanged(final Editable s) {

                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                retrofitService.getSchool(s.toString()).enqueue(new Callback<SchoolData>() {
                                    @Override
                                    public void onResponse(@NonNull Call<SchoolData> call, @NonNull Response<SchoolData> response) {
                                        if (response.isSuccessful()) {
                                            body = response.body();
                                            if (body.message.equals("success")) {

                                                // specify an adapter (see also next example)
                                                mAdapter = new RegisterSchoolAdapter(body);
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
                        },
                        DELAY
                );
            }
        });

        final GestureDetector gestureDetector = new GestureDetector(getApplicationContext(),new GestureDetector.SimpleOnGestureListener() {

            //누르고 뗄 때 한번만 인식하도록 하기위해서
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });


        RecyclerView.OnItemTouchListener onItemTouchListener = new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                //손으로 터치한 곳의 좌표를 토대로 해당 Item의 View를 가져옴
                View childView = rv.findChildViewUnder(e.getX(),e.getY());

                //터치한 곳의 View가 RecyclerView 안의 아이템이고 그 아이템의 View가 null이 아니라
                //정확한 Item의 View를 가져왔고, gestureDetector에서 한번만 누르면 true를 넘기게 구현했으니
                //한번만 눌려서 그 값이 true가 넘어왔다면
                if(childView != null && gestureDetector.onTouchEvent(e)){

                    //현재 터치된 곳의 position을 가져오고
                    int currentPosition = rv.getChildAdapterPosition(childView);

                    //해당 위치의 Data를 가져옴
                    SchoolData.Result currentItemSchool = body.results[currentPosition];
                    Toast.makeText(getApplicationContext(), currentItemSchool.SCHUL_NM, Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        };

        schoolList.addOnItemTouchListener(onItemTouchListener);

        final Intent getIntent = getIntent();
        final Intent newIntent = new Intent(getApplicationContext(), RegisterCompletedActivity.class);  // TODO register complete class

        final String name = getIntent.getExtras().getString("SCHUL_NM");
        final String id = getIntent.getExtras().getString("id");
        final String pw = getIntent.getExtras().getString("pw");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newIntent.putExtra("SCHUL_NM", name);
                newIntent.putExtra("id", id);
                newIntent.putExtra("pw", pw);

                startActivity(newIntent);
            }
        });
    }
}
