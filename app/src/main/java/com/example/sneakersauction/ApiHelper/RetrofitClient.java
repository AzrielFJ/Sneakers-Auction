package com.example.sneakersauction.ApiHelper;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {

    public static Retrofit RETROFIT     = null;
    public static final String BASE_URL_API = "http://192.168.8.100/api/";
    public static final String BASE_URL_FILE = "http://192.168.8.100/images/";
    public static Retrofit getClient(String BASE_URL_API){
        if(RETROFIT==null){
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new LoggingInterceptor())
                    .build();
            RETROFIT = new Retrofit.Builder()
                    .baseUrl(BASE_URL_API)
                    .client( client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return RETROFIT;
    }


    public static ApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(ApiService.class);
    }
    public static ApiService getAPIService2(){
        return RetrofitClient.getClient(BASE_URL_FILE).create(ApiService.class);
    }

}
