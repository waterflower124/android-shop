package com.anas.fishday.screens.main.fragments.services;

import com.anas.fishday.entities.Product;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.screens.main.fragments.services.interfaces.ServicesInteractor;
import com.anas.fishday.screens.main.fragments.services.interfaces.ServicesPresenter;

import java.util.List;

public class ServicesPresenterImpl implements ServicesPresenter{

    private ServicesInteractor interactor;
    private ServicesModel model;

    public ServicesPresenterImpl(ServicesInteractor interactor) {
        this.interactor = interactor;
        model = new ServicesModel(this);
    }

    @Override
    public void getServices() {
        model.getServices();
    }

    @Override
    public void onGettingProductsSucceed(ResponseEntity<List<Product>> productResponseEntity) {
        interactor.showProducts(productResponseEntity);
    }

    @Override
    public void onGettingProductsFailed(String message) {
        interactor.showErrorForGettingProducts(message);
    }

    @Override
    public void onStop() {
        model.clearDisposable();
    }
}
