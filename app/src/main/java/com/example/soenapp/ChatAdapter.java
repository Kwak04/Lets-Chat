package com.example.soenapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<Chat> mDataset;
    private String myUserKey;

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView chat_text, chat_person, chat_time, who;

        public MyViewHolder(View v) {
            super(v);
            chat_text = v.findViewById(R.id.chat_text);
            chat_person = v.findViewById(R.id.chat_person);
            chat_time = v.findViewById(R.id.chat_time);
            who = v.findViewById(R.id.chat_who);
        }
    }

    public ChatAdapter(List<Chat> myDataset, String my_user_key) {
        mDataset = myDataset;
        myUserKey = my_user_key;
    }

    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_object, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.chat_text.setText(mDataset.get(position).text);
        holder.chat_person.setText(mDataset.get(position).person);
        holder.chat_time.setText(mDataset.get(position).time);

        if (myUserKey.equals(mDataset.get(position).user_key)) {
            holder.who.setText("나");
        } else {
            holder.who.setText("너");
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
