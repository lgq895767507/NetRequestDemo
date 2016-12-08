package com.flw.netrequestdemo;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import com.flw.netrequestdemo.adapter.RecycleAdapter;
import com.flw.netrequestdemo.entity.NewsInfo;
import com.flw.netrequestdemo.obserable.RequestObserable;
import com.flw.netrequestdemo.service.RetrofitService;


import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAct extends BaseActivity {

    @BindView(R.id.mRecyclerview)
    RecyclerView mRecyclerview;

    private LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
        //创建retrofit实例
        linearLayoutManager= new LinearLayoutManager(this);
        initView();
    }

    private void initView() {

        Retrofit retrofit = new Retrofit.Builder()
                //设置baseUrl,注意baseUrl 应该以/ 结尾。
                .baseUrl("http://api.jisuapi.com/news/")
                //使用Gson解析器,可以替换其他的解析器
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        //利用Retrofit 创建服务接口
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        //实现接口参数
        Call<NewsInfo> call = retrofitService.getListDatas("头条","0",20,BaseActivity.getAPPKEY());

        //执行异步请求,这里的onResponse(),onFailure()都是在主线程中运行的
        call.enqueue(new Callback<NewsInfo>() {
            @Override
            public void onResponse(Call<NewsInfo> call, Response<NewsInfo> response) {
                if (response.isSuccessful()){
                    RecycleAdapter mAdapter = new RecycleAdapter(getApplicationContext(),response.body().getResult().getList());      //初始化适配器
                    RequestObserable.itemOnClick(mAdapter);                             //item监听时间
                    mRecyclerview.setLayoutManager(linearLayoutManager);
                    mRecyclerview.setAdapter(mAdapter);
                }

            }

            @Override
            public void onFailure(Call<NewsInfo> call, Throwable t) {
                Log.i("lgq","onFailure:"+t);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }
}
