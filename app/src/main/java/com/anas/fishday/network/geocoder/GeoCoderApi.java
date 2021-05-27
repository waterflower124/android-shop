package com.anas.fishday.network.geocoder;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Anas on 3/8/2018.
 */

public class GeoCoderApi {

    private static final String GOOGLE_MAPS_API_URL = "https://maps.googleapis.com/maps/api/";

    public static GeoCoderService getInstance(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GOOGLE_MAPS_API_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build();
        GeoCoderService service = retrofit.create(GeoCoderService.class);
        return service;
    }

}
