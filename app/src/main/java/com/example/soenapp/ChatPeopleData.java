package com.example.soenapp;

class ChatPeopleData {
    String message;
    Result[] results;

    public class Result {
        String user_key;
        String name;
        boolean is_liked;

        public void setLiked(boolean newValue){
            is_liked = newValue;
        }
    }
}
