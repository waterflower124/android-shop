package com.anas.fishday.network.modules;

import android.util.Log;

import com.anas.fishday.network.ApplicationScope;
import com.anas.fishday.storage.FishDayStorage;

import java.io.IOException;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;

/**
 * Created by Anas on 10/1/2017.
 */
@Module
public class NetworkModule {

    private static String API_KEY = "1d7801c576b33db841d59216d8cf91d4";

    @ApplicationScope
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }


    @ApplicationScope
    @Provides
    Interceptor provideInterceptor() {
        return chain -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder();
            requestBuilder.header("Content-Type", "application/json");
            requestBuilder.header("ApiKey", "key=" + API_KEY);
                if (FishDayStorage.getUser() != null) {

                    String token = FishDayStorage.getUser().getAuthToken();
                    requestBuilder.header("Authorization", token);
                }

            requestBuilder.method(original.method(), original.body());
            if (original.body() != null){
                Buffer buffer = new Buffer();
                original.body().writeTo(buffer);
                Log.d("LOGGGGG ", buffer.readUtf8());
            }
            Request request = requestBuilder.build();
            return chain.proceed(request);
        };
    }

    @ApplicationScope
    @Provides
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor, Interceptor interceptor) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(httpLoggingInterceptor)
                .interceptors().add(interceptor);
        return httpClient.build();
    }
}
