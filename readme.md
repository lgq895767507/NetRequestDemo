### OkHttp的使用
##### OkHttp现在最新的的版本在github上是3.5.0，它是来自Square公司。有以下几个优点：
  * 支持 SPDY[^footnote] ，允许连接同一主机的所有请求分享一个socket。
  * 如果SPDY不可用，会使用连接池减少请求延迟。
  * 使用GZIP压缩下载内容，且压缩操作对用户是透明的。
  * 利用响应缓存来避免重复的网络请求。
[^footnote]:SPDY(读作“SPeeDY”)是Google开发的基于TCP的应用层协议，用以最小化网络延迟，提升网络速度，优化用户的网络使用体验。SPDY并不是一种用于替代HTTP的协议，而是对HTTP协议的增强。新协议的功能包括数据流的多路复用、请求优先级以及HTTP报头压缩。谷歌表示，引入SPDY协议后，在实验室测试中页面加载速度比原先快64%。

##### 接下来看下简单的使用说明：
* 在gradle中配置：
```java
compile 'com.squareup.okhttp3:okhttp:3.5.0'
```
* 初始化OkHttpClient对象以及Request对象
```java
OkHttpClient mOkHttpClient = new OkHttpClient();
Request request = new Request.Builder().url(uri).build();
```
##### OkHttp支持同步和异步请求
* OkHttp同步请求的时候，执行execute()方法，返回一个Response对象，在这里会阻塞线程，直到响应结束，所以是不能在主线程中执行。这里是无返回值的。
```java
Response response = mOkHttpClient.newCall(request).execute();
```
* OkHttp异步请求的时候，执行enqueue()方法，在enqueue()中实现一个Callback接口，因为这个操作是异步的，所以可以在主线程中使用。说的是响应数据是异步的，但是不能直接再此更新UI组件，因为他是在okhttp线程中，直接在此更新UI会报错。也许你会想到用Message对象将数据传到主线程中，使用Handle来接收并更新UI。而我这里使用的是Rxjava嵌入内部，被观察者获取到数据后传递给观察者，指定观察者的线程是Main主线程，就可以很方便的更新数据了。
```java
 mOkHttpClient.newCall(request).enqueue(new Callback() { 
   @Override   
   public void onFailure(Call call, IOException e) {     
       Log.i("lgq","error:"+e);   
     }   
   @Override   
     public void onResponse(Call call, Response response) throws IOException {  
        Log.i("lgq","okhttp response:"+response);  
        //封装了观察者对象代码
        RequestObserable.responNetRequset(mRecyclerview,response.body().string(),getApplicationContext());    
    }
});
```
* 贴出RequestObserable代码：
```java
public static void responNetRequset(final RecyclerView mRecyclerview, String result, final Context context) {

        Gson gson = new Gson();
        final NewsInfo newsInfo = gson.fromJson(result, NewsInfo.class);
        Observable.create(new ObservableOnSubscribe<List<Contents>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Contents>> e) throws Exception {
                e.onNext(newsInfo.getResult().getList());
                e.onComplete();
            }
        }).observeOn(Schedulers.io())
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
  
```
* 以上代码涉及到Gson 这个框架，是用来解析json数据到Java对象，可以参考这个了解下：[你真的会用Gson吗?Gson使用指南（一）](http://www.jianshu.com/p/e740196225a4)
* OkHttp的使用流程大概简单的说明就这样。

> 1.okhttp-3.5.0.jar包大小342K。

------------------

### Volley的使用

##### Volley现在最新的的版本在github上是1.0.19，在2013年Google I/O大会上推出的网络通信框架。适用于进行数据量不大，但通信频繁的网络操作。
##### 简单的使用例子：
* 配置gradle
```java
compile 'com.mcxiaoke.volley:library:1.0.19'
```
* 在Application中配置全局单例的RequestQueue对象
```java
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
    }
```
* Volley有StringRequest 请求对象和JsonRequest请求对象，这里以JsonRequest为例：response即为响应返回获得的json数据。RequestObserable在上面已经解释了。
```java
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
```
* ok，无需过多的解释吧，那继续。在最后onStop()方法中关闭请求队列。
```java
 @Override
    protected void onStop() {
        super.onStop();
        //activity关闭的的时候取消请求队列
        App.getRequestQueue().cancelAll(VO_GET_TAG);
    }
```

> 1.library-1.0.19.jar大小为91.6K。

----------------------------

### Retrofit的使用
##### Retrofit现在最新的的版本在github上是2.1.0，也是Square公司的产品，Retrofit在2.0之后的版本和之前的变化很大，做出了优化和简化，所以直接引入的是2.1.0版本。Retrofit的特点：
* 它是一个可以用于Android和java的网络库，使用它可以简化我们网络操作的工作，提高效率和正确率。
* gradle引入,这里还引入了自己的gson库，可实现将Json数据转换为java对象。
```java
 //retrofit
    compile 'com.squareup.retrofit2:retrofit:2.1.0'
    compile 'com.squareup.retrofit2:converter-gson:2.1.0'
```
* 将rest API封装为java接口，我们根据业务需求来进行接口的封装，实际开发可能会封装多个不同的java接口以满足业务需求。（注解的形式）
```java
public interface RetrofitService {
    @GET("get")
    Call<NewsInfo> getListDatas(
            @Query("channel")String str,
            @Query("start")String start,
            @Query("num")int num,
            @Query("appkey")String appkey
            );
}
```
* 使用Retrofit生成接口实例。
```java
 Retrofit retrofit = new Retrofit.Builder()
                //设置baseUrl,注意baseUrl 应该以/ 结尾。
                .baseUrl("http://api.jisuapi.com/news/")
                //使用Gson解析器,可以替换其他的解析器
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        //利用Retrofit 创建服务接口
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
```
* rest风格，更安全和高效。
* 通过接口实例实现Call方法，而且实现Callback接口是异步请求，但是onResponse(),onFailure()都是在主线程中运行的。可以用来更新UI操作。
```java
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
```
* 操作上明显是比volley和okhttp简单多了。

> 1.retrofit-2.1.0.jar大小为86.2K。
