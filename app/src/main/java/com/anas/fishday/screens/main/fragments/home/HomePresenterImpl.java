package com.anas.fishday.screens.main.fragments.home;

import com.anas.fishday.entities.Category;
import com.anas.fishday.entities.Meter;
import com.anas.fishday.entities.Offer;
import com.anas.fishday.entities.Product;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.screens.main.fragments.home.interfaces.HomeInteractor;
import com.anas.fishday.screens.main.fragments.home.interfaces.HomePresenter;

import java.util.List;

/**
 * Created by Anas on 2/16/2018.
 */

public class HomePresenterImpl implements HomePresenter {

    private HomeInteractor interactor;
    private HomeModel model;

    public HomePresenterImpl(HomeInteractor interactor) {
        this.interactor = interactor;
        model = new HomeModel(this);
    }

    public void getProducts(int categoryId) {
        model.getProducts(categoryId);
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
    public void getOffers() {
        model.getOffers();
    }

    @Override
    public void onGettingOffersSucceed(ResponseEntity<List<Offer>> offerResponseEntity) {
        interactor.showOffers(offerResponseEntity);
    }

    @Override
    public void onGettingOffersFailed(String message) {
        interactor.showErrorForGettingOffers(message);
    }

    @Override
    public void getCategories() {
        model.getCategories();
    }

    @Override
    public void onGettingCategoriesSucceed(ResponseEntity<List<Category>> categoriesResponseEntity) {
        interactor.showCategories(categoriesResponseEntity);
    }

    @Override
    public void onGettingCategoriesFailed(String message) {
        interactor.showErrorForGettingCategories(message);
    }

    @Override
    public void getMeter() {
        model.getMeter();
    }

    @Override
    public void onGettingMeterSucceed(ResponseEntity<Meter> responseEntity) {
        interactor.showMeter(responseEntity);
    }

    @Override
    public void onGettingMeterFailed(String message) {
        interactor.showErrorForGettingMeter(message);
    }

    @Override
    public void onStop() {
        model.clearDisposable();
    }
}


