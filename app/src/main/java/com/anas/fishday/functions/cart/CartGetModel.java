package com.anas.fishday.functions.cart;

import android.util.Log;

import com.anas.fishday.app.FishDayApplication;
import com.anas.fishday.base.BaseModel;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.network.AppService;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CartGetModel extends BaseModel {

    private CartGetPresenter presenter;
    private CompositeDisposable compositeDisposable;
    private AppService appService;
    public CartGetModel(CartGetPresenter presenter) {
        this.presenter = presenter;
        compositeDisposable = new CompositeDisposable();
        initAppService();
    }

    private void initAppService() {
        appService = FishDayApplication.getWebServiceComponent().getAppService();
    }

    public void getCart(){
        Disposable disposable = appService.getCart()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);

        compositeDisposable.add(disposable);
    }

    private void onSuccess(ResponseEntity<Order> orderResponseEntity) {
        Log.d("4444444444", new Gson().toJson(orderResponseEntity));
        presenter.onGettingCartSuccess(orderResponseEntity);
    }

    private void onError(Throwable throwable) {
        presenter.onGettingCartError(throwable.getMessage());
    }

    @Override
    protected void clearDisposable() {
        compositeDisposable.clear();
    }
}
