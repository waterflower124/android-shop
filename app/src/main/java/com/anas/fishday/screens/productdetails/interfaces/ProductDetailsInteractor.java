package com.anas.fishday.screens.productdetails.interfaces;

import com.anas.fishday.entities.Product;
import com.anas.fishday.entities.ResponseEntity;

import java.util.List;

/**
 * Created by Anas on 2/16/2018.
 */

public interface ProductDetailsInteractor {

    void showErrorForGettingProductDetail(String message);
    void showProductDetail(ResponseEntity<Product> productResponseEntity);
}
