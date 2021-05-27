package com.anas.fishday.network.geocoder;

import com.anas.fishday.entities.AddressResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Anas on 3/9/2018.
 */

public interface GeoCoderService {
    @GET("geocode/json")
    Single<AddressResponse> getAddress(@Query("latlng") String latlng, @Query("key") String apiKey);
}
