package com.wekancode;

import android.content.Context;

import com.wekancode.request.DotReq;
import com.wekancode.request.LoginReq;
import com.wekancode.request.LoginResp;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public class RestClient {

  public static APIInterface apiInterface, apiInterface1;
  public static String BASE_URL = "http://dev.rest.taptodot.com/";
  public static String BASE_PAYMENT = "https://apps.novocinemas.com/";


  public static APIInterface getapiclient(Context context) {

    if (apiInterface == null) {

      HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
      httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

      OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
      OkHttpClient okHttpClient1 = new OkHttpClient().newBuilder()
          .connectTimeout(60, TimeUnit.SECONDS)
          .readTimeout(130, TimeUnit.SECONDS)
          .writeTimeout(60, TimeUnit.SECONDS)
          //                   .build();
          .addInterceptor(httpLoggingInterceptor).build();
      Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
          .client(okHttpClient1)
          .addConverterFactory(GsonConverterFactory.create()).build();

      apiInterface = retrofit.create(APIInterface.class);
    }

    return apiInterface;
  }

  public static APIInterface getapi(Context context) {
    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient okHttpClient1 = new OkHttpClient().newBuilder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(130, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        //                   .build();

        .addInterceptor(httpLoggingInterceptor).build();
    Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
        .client(okHttpClient1)
        .addConverterFactory(GsonConverterFactory.create()).build();

    apiInterface = retrofit.create(APIInterface.class);
    return apiInterface;
  }


  public interface APIInterface {

    @POST("v1/login")
    Call<LoginResp> getLoginResp(@Body LoginReq loginReq);


    @POST("v1/dots")
    Call<LoginResp> getDotResp(@Header("Authorization") String tkn, @Body DotReq loginReq);
  }
}
