package com.flw.netrequestdemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.okhttp)
    Button okhttp;
    @BindView(R.id.volley)
    Button volley;
    @BindView(R.id.retrofit)
    Button retrofit;
    @BindView(R.id.nohttp)
    Button nohttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.okhttp)
    void IntentOkhttp(){
        Intent intent = new Intent(this,OkHttpAct.class);
        startActivity(intent);
    }

    @OnClick(R.id.volley)
    void IntentVolley(){
        Intent intent = new Intent(this,VolleyAct.class);
        startActivity(intent);
    }

    @OnClick(R.id.retrofit)
    void IntentRetrofit(){
        Intent intent = new Intent(this,RetrofitAct.class);
        startActivity(intent);
    }

    @OnClick(R.id.nohttp)
    void IntentNoHttp(){
        Intent intent = new Intent(this,NoHttpAct.class);
        startActivity(intent);
    }


}
