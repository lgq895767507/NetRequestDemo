package com.flw.netrequestdemo;

import android.app.Application;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;

/**
 * Created by lgq on 2016/12/8.
 */

public class App extends Application{

    //volley请求队列
    public static RequestQueue requestQueue;
    //noHttp请求队列
    public static com.yolanda.nohttp.rest.RequestQueue noRequest;

    public static RequestQueue getRequestQueue(){
        return requestQueue;
    }

    public static com.yolanda.nohttp.rest.RequestQueue getNoHttpRequest(){ return noRequest;}

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化请求队列
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        //初始化noHttp请求队列
        noRequest = NoHttp.newRequestQueue();


        //一般初始化NoHttp方法，可以选择高级的自定义
        NoHttp.initialize(this,new NoHttp.Config()
        .setConnectTimeout(30*1000)
        .setReadTimeout(30*1000)
        .setNetworkExecutor(new OkHttpNetworkExecutor()));//切换okhttp作为网络层
        //开启调试功能
        Logger.setDebug(true);
        Logger.i("App onCreate()");//默认的Log的TAG是“NoHttp”字符串。
    }


}
