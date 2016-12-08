package com.flw.netrequestdemo;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.flw.netrequestdemo.obserable.RequestObserable;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.ButterKnife;


public class VolleyAct extends BaseActivity {

    @BindView(R.id.mRecyclerview)
    RecyclerView mRecyclerview;

    private static final String VO_GET_TAG = "VOLLEYACT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        ButterKnife.bind(this);

        //请求数据
        requsetDatas();
    }

    private void requsetDatas() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, uri, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("lgq","volley response:"+response);
                RequestObserable.responNetRequset(mRecyclerview,response.toString(),getApplicationContext());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("lgq","volley error:"+error);
            }
        });
        //setting tag for cancel
        jsonObjectRequest.setTag(VO_GET_TAG);
        App.getRequestQueue().add(jsonObjectRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //activity关闭的的时候取消请求队列
        App.getRequestQueue().cancelAll(VO_GET_TAG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }
}
