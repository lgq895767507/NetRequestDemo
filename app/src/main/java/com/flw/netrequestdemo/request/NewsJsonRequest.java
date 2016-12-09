package com.flw.netrequestdemo.request;


import com.flw.netrequestdemo.entity.NewsInfo;
import com.google.gson.Gson;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.RestRequest;
import com.yolanda.nohttp.rest.StringRequest;


/**
 * Created by lgq on 2016/12/9.
 */

public class NewsJsonRequest extends RestRequest<NewsInfo> {

    private Class<NewsInfo> newsInfo;


    public NewsJsonRequest(String url, Class<NewsInfo> newsInfo) {
        this(url, RequestMethod.GET, newsInfo);
    }

    public NewsJsonRequest(String url, RequestMethod requestMethod,Class<NewsInfo> newsInfo) {
        super(url, requestMethod);
        this.newsInfo = newsInfo;
    }

    @Override
    public NewsInfo parseResponse(Headers responseHeaders, byte[] responseBody) throws Throwable {
        String response = StringRequest.parseResponseString(responseHeaders,responseBody);
        return  new Gson().fromJson(response,NewsInfo.class);
    }
}
