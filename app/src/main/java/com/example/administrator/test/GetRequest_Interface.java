package com.example.administrator.test;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GetRequest_Interface {

    //http://fy.iciba.com/ajax.php?a=fy&f=auto&t=auto&w=hello

    //@GET("ajax.php?a=fy&f=auto&t=auto")
    //Call<Translation> getCall(@Query(value ="w", encoded = true) String w);

    @FormUrlEncoded
    @POST("ajax.php?a=fy")
    Call<Translation> getCall(@Field("f") String f,@Field("t") String t,@Field("w") String w);



    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // getCall()是接受网络请求数据的方法
}
