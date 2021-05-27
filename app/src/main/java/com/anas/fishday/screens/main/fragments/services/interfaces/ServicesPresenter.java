package com.anas.fishday.screens.main.fragments.services.interfaces;

import com.anas.fishday.base.BasePresenter;
import com.anas.fishday.entities.Product;
import com.anas.fishday.entities.ResponseEntity;

import java.util.List;

public interface ServicesPresenter extends BasePresenter {

    void getServices();
    void onGettingProductsSucceed(ResponseEntity<List<Product>> productResponseEntity);
    void onGettingProductsFailed(String message);
}
