package com.anas.fishday.screens.main.fragments.home.interfaces;

import com.anas.fishday.base.BasePresenter;
import com.anas.fishday.entities.Category;
import com.anas.fishday.entities.Data;
import com.anas.fishday.entities.Meter;
import com.anas.fishday.entities.Offer;
import com.anas.fishday.entities.Product;
import com.anas.fishday.entities.ResponseEntity;

import java.util.List;

/**
 * Created by Anas on 2/16/2018.
 */

public interface HomePresenter extends BasePresenter{

    void getProducts(int categoryId);
    void onGettingProductsSucceed(ResponseEntity<List<Product>>  productResponseEntity);
    void onGettingProductsFailed(String message);

    void getOffers();
    void onGettingOffersSucceed(ResponseEntity<List<Offer>>  productResponseEntity);
    void onGettingOffersFailed(String message);

    void getCategories();
    void onGettingCategoriesSucceed(ResponseEntity<List<Category>>  categoryResponseEntity);
    void onGettingCategoriesFailed(String message);

    void getMeter();
    void onGettingMeterSucceed(ResponseEntity<Meter>  responseEntity);
    void onGettingMeterFailed(String message);
}
