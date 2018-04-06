package com.test.hank.test2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        testAsyncGetRequest();
    }

    private static final String tag = "main2tag";
    private static final String url = "http://www.publicobject.com/helloworld.txt";
    private static final String urlhttps = "http://www.12306.cn/mormhweb/";

    //这是一个普通的okhttp异步get请求
    private void testAsyncGetRequest() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addNetworkInterceptor(new LoggingInterceptor()).build();//新建一个okhttpClient对象，并且设置拦截器
        Request request = new Request.Builder().url(urlhttps).build();//新建Request对象
        Callback callback = new Callback() {// 新建异步请求的回调函数
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(tag, "request Failed ; " + e.getLocalizedMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.d(tag, "onResponse:" + response.body().string());
                } else {
                    Log.d(tag, "onResponse failed");
                }
            }
        };
        okHttpClient.newCall(request).enqueue(callback);//用okhttpClient执行request，并且注册回调函数

    }


    /**
     *  这是按照官方的示例Interceptor改的，打印日志的方式换成了Log.d().
     */
    class LoggingInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            //第一步，获得chain内的request
            Request request = chain.request();
            Log.d(tag, "intercept-request:" + request.url());
            //第二步，用chain执行request
            Response response = chain.proceed(request);
            Log.d(tag, "intercept-response" + "-" + response.request().url());
            //第三步，返回response
            return response;
        }
    }
}