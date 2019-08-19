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

    String ip = "15.164.219.136";  // aws EC2 ip 입력
    String URL = "http://" + ip + ":3000/";

    @FormUrlEncoded
    @POST("/user/privacy")
    Call<LoginData> postData(@FieldMap HashMap<String, Object> param);


    @GET("/user/privacy/register/school")
    Call<SchoolData> getSchool(@Query("schoolname") String schoolname);

}
