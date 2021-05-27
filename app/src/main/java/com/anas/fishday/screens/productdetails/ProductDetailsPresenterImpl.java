package com.anas.fishday.screens.productdetails;

import com.anas.fishday.entities.Product;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.screens.productdetails.interfaces.ProductDetailsInteractor;
import com.anas.fishday.screens.productdetails.interfaces.ProductDetailsPresenter;

import java.util.List;

/**
 * Created by Anas on 2/16/2018.
 */

public class ProductDetailsPresenterImpl implements ProductDetailsPresenter {

    private ProductDetailsInteractor interactor;
    private ProductDetailsModel model;

    public ProductDetailsPresenterImpl(ProductDetailsInteractor interactor) {
        this.interactor = interactor;
        model = new ProductDetailsModel(this);
    }

    @Override
    public void onStop() {
        model.clearDisposable();
    }

    @Override
    public void getProductDetail(String product_id) {
        model.getProductDetail(product_id);
    }

    @Override
    public void onGettingProductDetailSucceed(ResponseEntity<Product> productResponseEntity) {
        interactor.showProductDetail(productResponseEntity);
    }

    @Override
    public void onGettingDetailFailed(String message) {
        interactor.showErrorForGettingProductDetail(message);
    }
}
