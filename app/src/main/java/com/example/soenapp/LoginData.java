package com.example.soenapp;

public class LoginData {
    public String message;
    public Result[] results;

    public class Result {
        public String user_token;
        public String user_key;
        public String name;
        public String school;
        public String school_code;
    }
}
