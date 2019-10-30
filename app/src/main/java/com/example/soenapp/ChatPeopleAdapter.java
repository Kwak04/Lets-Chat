package com.example.soenapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatPeopleAdapter extends RecyclerView.Adapter<ChatPeopleAdapter.ViewHolder> {

    private ChatPeopleData mDataset;
    private String userKey;
    private Context context;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(RetrofitService.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private RetrofitService retrofitService = retrofit.create(RetrofitService.class);

    private SimpleMessageData messageBody;

    SharedPreferences sharedPreferences;



    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CheckBox favorite;
        boolean isLiked;

        ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.tv_people_name);
            favorite = v.findViewById(R.id.cb_favorites);

            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int adapterPosition = getAdapterPosition();

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("user_key", userKey);
                    hashMap.put("liking_key", mDataset.results[adapterPosition].user_key);

                    if (!isLiked) {
                        isLiked = true;

                        retrofitService.addFavorite(hashMap).enqueue(new Callback<SimpleMessageData>() {
                            @Override
                            public void onResponse(@NonNull Call<SimpleMessageData> call, @NonNull Response<SimpleMessageData> response) {
                                if (response.isSuccessful()) {
                                    messageBody = response.body();
                                    if (Objects.requireNonNull(messageBody).message.equals("success")) {
                                        favorite.setChecked(isLiked);
                                        mDataset.results[adapterPosition].setLiked(isLiked);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<SimpleMessageData> call, @NonNull Throwable t) {
                                Toast.makeText(context, "실패", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        isLiked = false;

                        retrofitService.removeFavorite(hashMap).enqueue(new Callback<SimpleMessageData>() {
                            @Override
                            public void onResponse(@NonNull Call<SimpleMessageData> call, @NonNull Response<SimpleMessageData> response) {
                                if (response.isSuccessful()) {
                                    messageBody = response.body();
                                    if (Objects.requireNonNull(messageBody).message.equals("success")) {
                                        favorite.setChecked(isLiked);
                                        mDataset.results[adapterPosition].setLiked(isLiked);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<SimpleMessageData> call, @NonNull Throwable t) {
                                Toast.makeText(context, "실패", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }
    }

    ChatPeopleAdapter(ChatPeopleData data, String myUserKey, Context context) {
        mDataset = data;
        userKey = myUserKey;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatPeopleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_people_object, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mDataset.results[position].name);
        holder.favorite.setChecked(mDataset.results[position].is_liked);
        holder.isLiked = mDataset.results[position].is_liked;
    }

    @Override
    public int getItemCount() {
        return mDataset.results.length;
    }
}
