package com.example.soenapp;

public class ChatData {
    String message;

    String text;
    String person;
    String time;
    String user_key;
    String seq;

    @Override
    public String toString() {
        return "ChatData {" +
                "text='" + text + '\'' +
                ", person='" + person + '\'' +
                ", time='" + time + '\'' +
                ", user_key='" + user_key + '\'' +
                ", seq='" + seq + '\'' +
                '}';
    }
}



