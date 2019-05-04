package com.example.soenapp;

public class SchoolData {
    String message;
    Result[] results;

    public class Result {
        String code;
        String address;
        String name;
        String type;
    }

    @Override
    public String toString() {

        String info_string = "";

        if (message.equals("success")) {
            for (int i = 0 ; i<results.length; i++){
                info_string += ("code : " + results[i].code + " address : " + results[i].address + " name : " + results[i].name + " type : " + results[i].type + "\n-------------");
            }
        }
        return info_string;
    }
}
