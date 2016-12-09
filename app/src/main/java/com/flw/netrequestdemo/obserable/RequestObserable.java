package com.flw.netrequestdemo.obserable;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.flw.netrequestdemo.adapter.RecycleAdapter;
import com.flw.netrequestdemo.entity.Contents;
import com.flw.netrequestdemo.entity.NewsInfo;
import com.flw.netrequestdemo.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lgq on 2016/12/8.
 */

public class RequestObserable {

    /**
     * 用于观察子线程中请求得到的数据，请求成功后将数据发送给UI线程去处理。
     * 该类是做一个简单的封装
     * @param mRecyclerview 界面UI
     * @param result        请求数据的json数据
     * @param context       上下文
     */

    public static void responNetRequset(final RecyclerView mRecyclerview, String result, final Context context) {

        Gson gson = new Gson();
        final NewsInfo newsInfo = gson.fromJson(result, NewsInfo.class);
        Observable.create(new ObservableOnSubscribe<List<Contents>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Contents>> e) throws Exception {
                e.onNext(newsInfo.getResult().getList());
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Contents>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("lgq", "onSubscribe");
                    }

                    @Override
                    public void onNext(List<Contents> value) {
                        System.out.println("onNext()----Thread:" + Thread.currentThread().getName());
                        RecycleAdapter mAdapter = new RecycleAdapter(context, value);
                        //适配器实现监听事件
                        itemOnClick(mAdapter);
                        mRecyclerview.setLayoutManager(new LinearLayoutManager(context));
                        mRecyclerview.setAdapter(mAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("lgq", "onError:" + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i("lgq", "onComplete()");
                    }
                });
    }

    /**
     * 重载了相同的方法，第二个参数改为Object类型，这是使用NoHttp的时候，使用的是javaBean类型的jsonObject，返回的javaBean类型
     * 所以在重写的方法中就使用了GSON将response返回的string类型转换为javaBean类型。
     * @param mRecyclerview
     * @param object
     * @param context
     */
    public static void responNetRequset(final RecyclerView mRecyclerview, final Object object, final Context context){
        Observable.create(new ObservableOnSubscribe<List<Contents>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Contents>> e) throws Exception {
                if (object instanceof NewsInfo){
                    e.onNext(((NewsInfo) object).getResult().getList());
                }
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<List<Contents>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Contents> value) {
                RecycleAdapter mAdapter = new RecycleAdapter(context, value);
                //适配器实现监听事件
                itemOnClick(mAdapter);
                mRecyclerview.setLayoutManager(new LinearLayoutManager(context));
                mRecyclerview.setAdapter(mAdapter);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public static void itemOnClick(RecycleAdapter mAdapter) {
        mAdapter.setOnItemClickListener(new RecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.showToast(view.getContext(),"id="+position,Toast.LENGTH_SHORT);
            }
        });
    }
}
