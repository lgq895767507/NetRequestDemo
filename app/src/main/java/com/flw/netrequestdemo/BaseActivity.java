package com.flw.netrequestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {


    private static final String APPKEY = "2466aed299620170";
    public static final String uri = "http://api.jisuapi.com/news/get?channel=%E5%A4%B4%E6%9D%A1&start=0&num=20&appkey="+APPKEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    public static String getAPPKEY() {
        return APPKEY;
    }
}
