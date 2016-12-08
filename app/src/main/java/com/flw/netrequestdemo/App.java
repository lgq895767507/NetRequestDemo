package com.flw.netrequestdemo;

import android.app.Application;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by lgq on 2016/12/8.
 */

public class App extends Application{

    //volley请求队列
    public static RequestQueue requestQueue;

    public static RequestQueue getRequestQueue(){
        return requestQueue;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化请求队列
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        Log.i("lgq","APP oncreate()");
    }


}
