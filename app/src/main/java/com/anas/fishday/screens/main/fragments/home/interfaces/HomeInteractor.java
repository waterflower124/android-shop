package com.anas.fishday.screens.main.fragments.home.interfaces;

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

public interface HomeInteractor {

    void showErrorForGettingProducts(String message);
    void showProducts(ResponseEntity<List<Product>> productResponseEntity);

    void showErrorForGettingOffers(String message);
    void showOffers(ResponseEntity<List<Offer>> offersResponseEntity);

    void showErrorForGettingCategories(String message);
    void showCategories(ResponseEntity<List<Category>> productResponseEntity);

    void showErrorForGettingMeter(String message);
    void showMeter(ResponseEntity<Meter> responseEntity);
}
