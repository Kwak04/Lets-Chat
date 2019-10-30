package com.example.soenapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatPeopleAdapter extends RecyclerView.Adapter<ChatPeopleAdapter.ViewHolder> {

    private ChatPeopleData mDataset;
//    private ArrayList<String> mData;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.tv_people_name);
        }
    }

    ChatPeopleAdapter(ChatPeopleData data) {
        mDataset = data;
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
    }

    @Override
    public int getItemCount() {
        return mDataset.results.length;
    }
}
