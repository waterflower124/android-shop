package com.anas.fishday.screens.main.fragments.home;

import com.anas.fishday.app.FishDayApplication;
import com.anas.fishday.base.BaseModel;
import com.anas.fishday.entities.Category;
import com.anas.fishday.entities.Data;
import com.anas.fishday.entities.Meter;
import com.anas.fishday.entities.Offer;
import com.anas.fishday.entities.Product;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.network.AppService;
import com.anas.fishday.screens.main.fragments.home.interfaces.HomePresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Anas on 2/23/2018.
 */

public class HomeModel extends BaseModel {

    private AppService appService;
    private HomePresenter presenter;
    private CompositeDisposable disposable;

    public HomeModel(HomePresenter presenter) {
        this.presenter = presenter;
        initApiCall();
        disposable = new CompositeDisposable();
    }

    private void initApiCall() {
        appService = FishDayApplication.getWebServiceComponent().getAppService();
    }

    public void getProducts(int categoryId) {
        Disposable productsDisposable = appService.getProducts(categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRequestProductsSuccess, this::onRequestProductsError);
        disposable.add(productsDisposable);
    }

    private void onRequestProductsSuccess(ResponseEntity<List<Product>> responseEntity) {
        presenter.onGettingProductsSucceed(responseEntity);
    }
    private void onRequestProductsError(Throwable throwable) {
        presenter.onGettingProductsFailed(throwable.getMessage());
    }

    public void getOffers() {
        Disposable productsDisposable = appService.getOffers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRequestOffersSuccess, this::onRequestOffersError);
        disposable.add(productsDisposable);
    }

    private void onRequestOffersSuccess(ResponseEntity<List<Offer>> responseEntity) {
        presenter.onGettingOffersSucceed(responseEntity);
    }
    private void onRequestOffersError(Throwable throwable) {
        presenter.onGettingOffersFailed(throwable.getMessage());
    }

    public void getCategories() {
        Disposable categoriesDisposable = appService.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRequestCategoriesSuccess, this::onRequestCategoriesError);
        disposable.add(categoriesDisposable);
    }

    private void onRequestCategoriesSuccess(ResponseEntity<List<Category>> responseEntity) {
        presenter.onGettingCategoriesSucceed(responseEntity);
    }
    private void onRequestCategoriesError(Throwable throwable) {
        presenter.onGettingCategoriesFailed(throwable.getMessage());
    }

    public void getMeter() {
        Disposable categoriesDisposable = appService.getMeter()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRequestMeterSuccess, this::onRequestMeterError);
        disposable.add(categoriesDisposable);
    }

    private void onRequestMeterSuccess(ResponseEntity<Meter> responseEntity) {
        presenter.onGettingMeterSucceed(responseEntity);
    }
    private void onRequestMeterError(Throwable throwable) {
        presenter.onGettingMeterFailed(throwable.getMessage());
    }
    @Override
    protected void clearDisposable() {
        disposable.clear();
    }
}
