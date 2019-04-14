package com.example.soenapp;

public class SchoolData {
    String message;
    SchoolInfo[] schoolInfos;

    public class SchoolInfo {
        String code;
        String address;
        String name;
        String type;
    }

    @Override
    public String toString() {

        String info_string = "";

        if (message.equals("success")) {
            for (int i = 0 ; i<schoolInfos.length; i++){
                info_string += ("code : " + schoolInfos[i].code + " address : " + schoolInfos[i].address + " name : " + schoolInfos[i].name + " type : " + schoolInfos[i].type + "\n-------------");
            }
        }
        return info_string;
    }
}
