package com.anas.fishday.screens.productdetails.interfaces;

import com.anas.fishday.base.BasePresenter;
import com.anas.fishday.entities.Product;
import com.anas.fishday.entities.ResponseEntity;

import java.util.List;

/**
 * Created by Anas on 2/16/2018.
 */

public interface ProductDetailsPresenter extends BasePresenter {

    void getProductDetail(String product_id);
    void onGettingProductDetailSucceed(ResponseEntity<Product> productResponseEntity);
    void onGettingDetailFailed(String message);
}
