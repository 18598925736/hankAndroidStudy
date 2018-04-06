package com.test.hank.test2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private Button btn_test;

    private void initView() {
        btn_test = findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //        obtainDatasync();
                obtainDataAsync();
            }
        });
    }

    /**
     * 同步获取数据的请求.
     */
    public void obtainDatasync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //第一步，创建一个OkhttpClient对象；
                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
                    //第二步，创建一个Request对象
                    Request request = new Request.Builder()
                            .url("http://www.baidu.com")//请求接口。如果需要传参拼接到接口后面。
                            .build();//创建Request 对象
                    //第三步，创建一个Response对象来接收request的返回结果
                    Response response = client.newCall(request).execute();//得到Response 对象
                    //第四步，解析response对象
                    if (response.isSuccessful()) {
                        Log.d("kwwl", "response.code()==" + response.code());
                        Log.d("kwwl", "response.message()==" + response.message());
                        Log.d("kwwl", "res==" + response.body().string());
                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                    } else {
                        Log.d("kwwl", "failed");
                    }
                } catch (Exception e) {
                    Log.d("kwwl", "Exception " + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Request request;

    /**
     * 异步获取数据请求,下面是最常用的关于Okhttp的功能；
     * 但是这样未免显得太肤浅了。我要试试更高深的用法。比如，拦截器。
     */
    private void obtainDataAsync() {
        // 同样，第一步，创建OkhttpClient对象；创建OkHttpClient也有多种方式，下面是很普通new方式
        OkHttpClient client = new OkHttpClient();
        //还有另一种方式，看到下面的addInterceptor没有?这个就是添加应用拦截器，专门拦截 回来的response，看看是否要原样放行。
        client = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();

        //第二步·，创建Request对象,包括参数的构造，
        // 在这里，构造参数有两种方式，get和post
        // get 构造参数，是在URL的后面用？和 & ， = 来连接所有参数
        // 而post，则是用FormBody的方式来封装所有参数。然后把FormBody作为request调用方法的实参，如下：

        //传参方式常用的就2种(post另外再分两种)
        //下面是1.get方式，实验结果，下面的get方式访问百度，能成功获取网页源码
        request = new Request.Builder().url("http://www.baidu.com").build();

        // 下面是2.1 post方式，实验结果，post方式让他在body中多了一个参数，反而不能成功访问了。返回值并不是200，所以证明，post方式传参成功了，只有传参成功才能影响实验结果
//        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体
//        formBody.add("username", "zhangsan");//传递键值对参数
//
//        request = new Request.Builder()//创建Request 对象。
//                .url("http://www.baidu.com")
//                .post(formBody.build())//传递请求体
//                .build();
//        // 下面还有一种2.2 post传参方式，使用RequestBody来封装json或者File。
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//确定数据类型为json格式，
//        String jsonStr = "{\"username\":\"lisi\",\"nickname\":\"李四\"}";//构造一个json数据字符串
//        RequestBody body = RequestBody.create(JSON, jsonStr);//构造RequestBody对象
//        request = new Request.Builder()
//                .url("http://www.baidu.com")
//                .post(body)
//                .build();//最后，构建request


        //第三步，创建一个Callback对象，用来接收异步请求
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("kwwl", "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("kwwl:code", "" + response.code());
                if (response.isSuccessful()) {//回调的方法执行在子线程。
                    Log.d("kwwl", "获取数据成功了");
                    Log.d("kwwl", "response.code()==" + response.code());
                    Log.d("kwwl", "response.body().string()==" + response.body().string());
                } else {
                    Log.d("kwwl", "获取数据失败");
                }
            }
        };
        //最后将request和callback作为参数 来执行okHttpClient的newCall方法
        client.newCall(request).enqueue(callback);
    }

    class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Log.d("Interceptor", String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Log.d("Interceptor", String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            return response;
        }
    }
}

