package com.example.soenapp;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {

    String homeIP = "119.196.210.238";  // 집 포트 포워딩 IP
    String localIP = "10.0.2.2";  // 로컬 서버 (에뮬레이터 구동 시) IP

    String ip = homeIP;
    String URL = "http://" + ip + ":3000/";

    // 로그인
    @FormUrlEncoded
    @POST("/user/privacy")
    Call<LoginData> postData(@FieldMap HashMap<String, Object> param);

    // 회원가입 - 학교 정보
    @GET("/user/privacy/register/school")
    Call<SchoolData> getSchool(@Query("schoolname") String schoolname);

    // 회원가입
    @FormUrlEncoded
    @POST("/user/privacy/register/next")
    Call<RegisterData> register(@FieldMap HashMap<String, Object> param);

    // 회원가입 - 아이디 중복 확인
    @GET("/user/privacy/register/idcheck")
    Call<SimpleMessageData> checkID(@Query("id") String id);

    // 같은 채팅방에 속해 있는 사람 목록
    @GET("/user/main/chatpeople")
    Call<ChatPeopleData> showChatPeople(@Query("room_key") String room_key, @Query("user_key") String user_key);

    // 즐겨찾기 추가
    @FormUrlEncoded
    @POST("/user/main/marking")
    Call<SimpleMessageData> addFavorite(@FieldMap HashMap<String, Object> param);

    // 즐겨찾기 취소
    @FormUrlEncoded
    @POST("/user/main/unbookmark")
    Call<SimpleMessageData> removeFavorite(@FieldMap HashMap<String, Object> param);

    // 즐겨찾기 확인
    @GET("/user/main/markcheck")
    Call<ChatPeopleData> checkFavorite(@Query("user_key") String user_key);
}
