package com.flw.netrequestdemo;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.flw.netrequestdemo.entity.NewsInfo;
import com.flw.netrequestdemo.obserable.RequestObserable;
import com.flw.netrequestdemo.request.NewsJsonRequest;
import com.flw.netrequestdemo.utils.ToastUtils;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.NotFoundCacheError;
import com.yolanda.nohttp.error.ParseError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Response;

import java.net.ProtocolException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoHttpAct extends BaseActivity {

    @BindView(R.id.mRecyclerview)
    RecyclerView mRecyclerview;

    private Object object = new Object();         //tag

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_http);
        ButterKnife.bind(this);
        //请求数据
        requestDatas();
    }

    private void requestDatas() {

        //创建请求对象
        NewsJsonRequest newsJsonRequest = new NewsJsonRequest(uri, NewsInfo.class);
        //调用异步请求
        App.getNoHttpRequest().add(0, newsJsonRequest, new OnResponseListener<NewsInfo>() {
            @Override
            public void onStart(int what) {
                // 这里可以show()一个wait dialog。
            }

            @Override
            public void onSucceed(int what, Response<NewsInfo> response) {
                if (response.responseCode() == 200) {
                    NewsInfo mDatas = response.get();//请求成功
                    RequestObserable.responNetRequset(mRecyclerview, mDatas, getApplicationContext());
                }
            }

            @Override
            public void onFailed(int what, Response<NewsInfo> response) {
                Exception e = response.getException();
                if (e instanceof NetworkError) {// 网络不好
                    ToastUtils.showToast(getApplicationContext(), "网络不好", Toast.LENGTH_SHORT);
                } else if (e instanceof TimeoutError) {// 请求超时
                    ToastUtils.showToast(getApplicationContext(), "请求超时", Toast.LENGTH_SHORT);
                } else if (e instanceof UnKnownHostError) {// 找不到服务器
                    ToastUtils.showToast(getApplicationContext(), "找不到服务器", Toast.LENGTH_SHORT);
                } else if (e instanceof URLError) {// URL是错的
                    ToastUtils.showToast(getApplicationContext(), "URL是错的", Toast.LENGTH_SHORT);
                } else if (e instanceof NotFoundCacheError) {
                    // 这个异常只会在仅仅查找缓存时没有找到缓存时返回
                    ToastUtils.showToast(getApplicationContext(), "这个异常只会在仅仅查找缓存时没有找到缓存时返回", Toast.LENGTH_SHORT);
                } else if (e instanceof ProtocolException) {
                    ToastUtils.showToast(getApplicationContext(), "ProtocolException", Toast.LENGTH_SHORT);
                } else if (e instanceof ParseError) {
                    ToastUtils.showToast(getApplicationContext(), "ParseError", Toast.LENGTH_SHORT);
                } else {
                    ToastUtils.showToast(getApplicationContext(), "error_unknow", Toast.LENGTH_SHORT);
                }

            }

            @Override
            public void onFinish(int what) {
                // 这里可以dismiss()上面show()的wait dialog。
            }
        });
        newsJsonRequest.setCancelSign(object);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getNoHttpRequest().cancelBySign(object);
        this.finish();
    }
}
