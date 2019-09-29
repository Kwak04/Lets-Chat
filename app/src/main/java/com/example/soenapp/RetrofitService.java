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

    String ip = "119.196.210.238";  // aws EC2 ip 입력
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
}
