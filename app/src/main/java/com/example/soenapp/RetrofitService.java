package com.example.soenapp;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitService {

    String ip = "54.180.123.66";
    String URL = "http://" + ip + ":3000/";

    @FormUrlEncoded
    @POST("/user/privacy")
    Call<LoginData> postData(@FieldMap HashMap<String, Object> param);


    @GET("/user/privacy/register/school")
    Call<SchoolData> getSchool(@FieldMap HashMap<String, Object> param);


}
