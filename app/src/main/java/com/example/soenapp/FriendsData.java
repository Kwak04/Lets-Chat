package com.example.soenapp;

public class FriendsData {
    //TODO 서버 연결 - api 구현

    private String name = "홍길동";
    private int photo = 0;

    public FriendsData(String name, int photo) {
        this.name = name;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
