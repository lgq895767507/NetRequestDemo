package com.flw.netrequestdemo.service;


import com.flw.netrequestdemo.entity.NewsInfo;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lgq on 2016/12/8.
 */

public interface RetrofitService {
    @GET("get")
    Call<NewsInfo> getListDatas(
            @Query("channel")String str,
            @Query("start")String start,
            @Query("num")int num,
            @Query("appkey")String appkey
            );
}
