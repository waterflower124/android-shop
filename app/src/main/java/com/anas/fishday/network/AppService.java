package com.anas.fishday.network;

import com.anas.fishday.entities.BaseEntity;
import com.anas.fishday.entities.Category;
import com.anas.fishday.entities.City;
import com.anas.fishday.entities.Data;
import com.anas.fishday.entities.Meter;
import com.anas.fishday.entities.Offer;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.OrderItem;
import com.anas.fishday.entities.OrderNew;
import com.anas.fishday.entities.Product;
import com.anas.fishday.entities.RequestEntity;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.entities.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Anas on 2/15/2018.
 */

public interface AppService {

    /*                     User                    */
    @POST("users/signup")
    Single<ResponseEntity<User>> register(@Body User user);
    @POST("verifications")
    Single<ResponseEntity<User>> confirmRegistration(@Body User user);
    @POST("users/signin")
    Single<ResponseEntity<User>> login(@Body User user);
    @POST("users/create_guest")
    Single<ResponseEntity<User>> registerGuest(@Body User user);
    @GET("cities")
    Single<ResponseEntity<List<City>>> getCities();
    /*                      Home                      */
    @GET("home")
    Single<ResponseEntity<List<Product>>> getProducts(@Query("q[category_id_eq]") int categoryId);

    @GET("meter")
    Single<ResponseEntity<Meter>> getMeter();
    @GET("sliders")
    Single<ResponseEntity<List<Offer>>> getOffers();
    @GET("categories")
    Single<ResponseEntity<List<Category>>> getCategories();
    @GET("products/services")
    Single<ResponseEntity<List<Product>>> getServices();

    /*                    cart           */
    @GET("carts/show")
    Single<ResponseEntity<Order>> getCart();

    @POST("cart_items/create_or_update")
    Single<ResponseEntity<Order>> createOrderItem(@Body OrderItem orderItem);

    @DELETE("cart_items/{order_item_id}")
    Single<ResponseEntity<Order>> deleteOrderItem(@Path("order_item_id") int orderItemId);

    @PUT("carts/update_cart_items")
    Single<ResponseEntity<Product>> updateOrderItem(@Body Order order);

    /*                      Order                        */
    @GET("orders/last_incomplete_order")
    Single<ResponseEntity<Order>> getLastOrder();

    @PUT("carts/update_cart_items")
    Single<ResponseEntity<Order>> placeOrder(@Body Order order);

    @GET("orders")
    Single<ResponseEntity<List<Order>>> getMyOrders();

    @POST("orders")
    Single<ResponseEntity<Order>> completeOrder(@Body OrderNew orderNew);

    /*             product detail               */
    @GET("products/{product_id}")
    Single<ResponseEntity<Product>> getProductDetail(@Path("product_id") String product_id);

    //    logout
    @POST("users/logout")
    Single<ResponseEntity<User>> logout();
}
