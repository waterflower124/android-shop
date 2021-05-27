package com.anas.fishday.screens.main.fragments.services.interfaces;

import com.anas.fishday.entities.Product;
import com.anas.fishday.entities.ResponseEntity;

import java.util.List;

public interface ServicesInteractor {

    void showErrorForGettingProducts(String message);
    void showProducts(ResponseEntity<List<Product>> productResponseEntity);
}
