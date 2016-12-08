package com.flw.netrequestdemo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.flw.netrequestdemo.adapter.RecycleAdapter;
import com.flw.netrequestdemo.entity.Contents;
import com.flw.netrequestdemo.entity.NewsInfo;
import com.flw.netrequestdemo.obserable.RequestObserable;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpAct extends BaseActivity {

    @BindView(R.id.mRecyclerview)
    RecyclerView mRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        ButterKnife.bind(this);
        //initData
        apiGetInfo(uri);
    }

    //通过API获取网络数据
    private void apiGetInfo(String uri){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(uri).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("lgq","error:"+e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("lgq","okhttp response:"+response);
                RequestObserable.responNetRequset(mRecyclerview,response.body().string(),getApplicationContext());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }
}
