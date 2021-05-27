package com.anas.fishday.network.modules;

import com.anas.fishday.network.AppService;
import com.anas.fishday.network.ApplicationScope;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Anas on 10/1/2017.
 */
@Module(includes = NetworkModule.class)
public class AppServiceModule {
    public static final String BASE_URL = "";

    @ApplicationScope
    @Provides
    AppService provideAppService(Retrofit retrofit){
        return retrofit.create(AppService.class);
    }

    @ApplicationScope
    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient
            , JacksonConverterFactory factory, RxJava2CallAdapterFactory adapterFactory
            , @Named("base_url") String baseUrl){

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(factory)
                .addCallAdapterFactory(adapterFactory)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Named("base_url")
    public String provideBaseUrl(){
        return BASE_URL;
    }

//    @ApplicationScope
//    @Provides
//    GsonConverterFactory provideGsonConverterFactory(){
//        return GsonConverterFactory.create();
//    }


    @ApplicationScope
    @Provides
    ObjectMapper provideObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        objectMapper.disable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        return objectMapper;
    }

    @ApplicationScope
    @Provides
    JacksonConverterFactory provideJacksonConverterFactory(ObjectMapper objectMapper){
        return JacksonConverterFactory.create(objectMapper);
    }

    @ApplicationScope
    @Provides
    RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory(){
        return RxJava2CallAdapterFactory.create();
    }

}
