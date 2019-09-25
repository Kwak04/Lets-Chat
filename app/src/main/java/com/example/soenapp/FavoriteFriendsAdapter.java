package com.example.soenapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FavoriteFriendsAdapter extends RecyclerView.Adapter<FavoriteFriendsAdapter.MyViewHolder> {
    private FriendsData mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
//        public ImageView friendPhoto;
        public TextView friendName;
        public MyViewHolder(View v) {
            super(v);
//            friendPhoto = v.findViewById(R.id.friend_photo);
            friendName = v.findViewById(R.id.friend_name);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
//    public FavoriteFriendsAdapter(FriendsData myDataset) {
//        mDataset = myDataset;
//    }
    private ArrayList<FriendsData> friendsDataArrayList;
    FavoriteFriendsAdapter(ArrayList<FriendsData> friendsDataArrayList) {
        this.friendsDataArrayList = friendsDataArrayList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FavoriteFriendsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_friends_object, parent, false);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.friendName.setText(friendsDataArrayList.get(position).getName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return friendsDataArrayList.size();
    }
}
