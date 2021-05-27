package com.anas.fishday.screens.main.fragments.services;

import com.anas.fishday.app.FishDayApplication;
import com.anas.fishday.base.BaseModel;
import com.anas.fishday.entities.Product;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.network.AppService;
import com.anas.fishday.screens.main.fragments.home.HomeModel;
import com.anas.fishday.screens.main.fragments.home.interfaces.HomeInteractor;
import com.anas.fishday.screens.main.fragments.home.interfaces.HomePresenter;
import com.anas.fishday.screens.main.fragments.services.interfaces.ServicesInteractor;
import com.anas.fishday.screens.main.fragments.services.interfaces.ServicesPresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ServicesModel extends BaseModel {

    private AppService appService;
    private ServicesPresenter presenter;
    private CompositeDisposable disposable;

    public ServicesModel(ServicesPresenter presenter) {
        this.presenter = presenter;
        initApiCall();
        disposable = new CompositeDisposable();
    }

    private void initApiCall() {
        appService = FishDayApplication.getWebServiceComponent().getAppService();
    }

    @Override
    protected void clearDisposable() {
        disposable.clear();
    }

    public void getServices() {
        Disposable productsDisposable = appService.getServices()
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
}
