package com.wl.runzekeji.util.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceManager {
    private static final int DEFAULT_TIME_OUT = 5;//��ʱʱ�� 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private Retrofit mRetrofit;
    private RetrofitServiceManager(){
        // create OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//���ӳ�ʱʱ��
        builder.writeTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//д���� ��ʱʱ��
        builder.readTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS);//��������ʱʱ��

        // add commonInterceptor
        BasicParamsInterceptor commonInterceptor = new BasicParamsInterceptor.Builder()
                .addHeaderParam("paltform","android")
                .addHeaderParam("userToken","1234343434dfdfd3434")
                .addHeaderParam("userId","123445")
                .build();
        builder.addInterceptor(commonInterceptor);


        // create RetrofitClient
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.douban.com/v2/movie/")
                .build();
    }

    private static class SingletonHolder{
        private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
    }

    /**
     * ��ȡRetrofitServiceManager
     * @return
     */
    public static RetrofitServiceManager getInstance(){
        return SingletonHolder.INSTANCE;
    }

    /**
     * ��ȡ��Ӧ��Service
     * @param service Service �� class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service){
        return mRetrofit.create(service);
    }
}
