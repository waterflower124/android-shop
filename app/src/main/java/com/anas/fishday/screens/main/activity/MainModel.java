package com.anas.fishday.screens.main.activity;

import android.util.Log;

import com.anas.fishday.app.FishDayApplication;
import com.anas.fishday.base.BaseModel;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.entities.User;
import com.anas.fishday.network.AppService;
import com.anas.fishday.screens.main.activity.interfaces.MainPresenter;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Anas on 2/26/2018.
 */

public class MainModel extends BaseModel{

    private MainPresenter presenter;
    private CompositeDisposable compositeDisposable;
    private AppService appService;
    public MainModel(MainPresenter presenter) {
        this.presenter = presenter;
        compositeDisposable = new CompositeDisposable();
        initAppService();
    }

    private void initAppService() {
        appService = FishDayApplication.getWebServiceComponent().getAppService();
    }

//    public void getLastOrder(){
//        Disposable disposable = appService.getLastOrder()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(this::onSuccess, this::onError);
//
//        compositeDisposable.add(disposable);
//    }
//
//    private void onSuccess(ResponseEntity<Order> orderResponseEntity) {
////        Log.d("4444444444", new Gson().toJson(orderResponseEntity));
//        presenter.onGettingLastOrderSuccess(orderResponseEntity);
//    }
//
//    private void onError(Throwable throwable) {
//        presenter.onGettingLastOrderFailure(throwable.getMessage());
//    }
    @Override
    protected void clearDisposable() {
        compositeDisposable.clear();
    }

    public void logout() {

        Log.d("4444444444", "presenter logout before");
        Disposable disposable = appService.logout()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLogoutSuccess, this::onLogoutError);

        compositeDisposable.add(disposable);
        Log.d("4444444444", "presenter logout after");
    }

    private void onLogoutSuccess(ResponseEntity<User> orderResponseEntity) {
        Log.d("4444444444", new Gson().toJson(orderResponseEntity));
        presenter.onGettingLogoutSuccess(orderResponseEntity);
    }

    private void onLogoutError(Throwable throwable) {
        Log.d("444444444444444", throwable.getMessage());
        presenter.onGettingLastLogoutFailure(throwable.getMessage());
    }
}
