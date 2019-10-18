package com.example.soenapp;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import static com.example.soenapp.R.drawable.chat_box_me;
import static com.example.soenapp.R.drawable.chat_box_you;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<ChatData> mDataset;
    private String myUserKey;

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView chat_text, chat_person, chat_time;
        public LinearLayout layoutChatObject, layoutChatContainer,
                            layoutChatFloor1, layoutChatFloor2;

        public MyViewHolder(View v) {
            super(v);
            chat_text = v.findViewById(R.id.chat_text);
            chat_person = v.findViewById(R.id.chat_person);
            chat_time = v.findViewById(R.id.chat_time);

            layoutChatObject = v.findViewById(R.id.layout_chat_object);
            layoutChatContainer = v.findViewById(R.id.layout_chat_container);
            layoutChatFloor1 = v.findViewById(R.id.layout_chat_floor1);
            layoutChatFloor2 = v.findViewById(R.id.layout_chat_floor2);
        }
    }

    public ChatAdapter(List<ChatData> myDataset, String my_user_key) {
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

        // myUserKey 가 메시지의 user_key 와 일치할 때
        // mDataset.user_key 는 서버에서 받아 오는 값임
        // myUserKey 는 로그인했을 때 SharedPreferences 에 저장되어 있는 값임
        LinearLayout.LayoutParams floor1LayoutParams = (LinearLayout.LayoutParams) holder.layoutChatFloor1.getLayoutParams();
        LinearLayout.LayoutParams floor2LayoutParams = (LinearLayout.LayoutParams) holder.layoutChatFloor2.getLayoutParams();

        if (myUserKey.equals(mDataset.get(position).user_key)) {
            holder.layoutChatObject.setGravity(Gravity.END);
            holder.layoutChatContainer.setBackgroundResource(chat_box_me);

            floor1LayoutParams.gravity = Gravity.END;
            floor2LayoutParams.gravity = Gravity.END;
            holder.layoutChatFloor1.setLayoutParams(floor1LayoutParams);
            holder.layoutChatFloor2.setLayoutParams(floor2LayoutParams);
        } else {
            holder.layoutChatObject.setGravity(Gravity.START);
            holder.layoutChatContainer.setBackgroundResource(chat_box_you);

            floor1LayoutParams.gravity = Gravity.START;
            floor2LayoutParams.gravity = Gravity.START;
            holder.layoutChatFloor1.setLayoutParams(floor1LayoutParams);
            holder.layoutChatFloor2.setLayoutParams(floor2LayoutParams);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
