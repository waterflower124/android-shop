package com.anas.fishday.screens.productdetails.dialog;

import android.util.Log;

import com.anas.fishday.app.FishDayApplication;
import com.anas.fishday.base.BaseModel;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.OrderItem;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.network.AppService;
import com.anas.fishday.screens.main.activity.interfaces.MainPresenter;
import com.anas.fishday.screens.productdetails.dialog.interfaces.CreateOrderItemPresenter;
import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Anas on 2/26/2018.
 */

public class CreateOrderItemModel extends BaseModel{

    private CreateOrderItemPresenter presenter;
    private CompositeDisposable compositeDisposable;
    private AppService appService;
    public CreateOrderItemModel(CreateOrderItemPresenter presenter) {
        this.presenter = presenter;
        compositeDisposable = new CompositeDisposable();
        initAppService();
    }

    private void initAppService() {
        appService = FishDayApplication.getWebServiceComponent().getAppService();
    }

    public void createOrderItem(OrderItem orderItem){
        Disposable disposable = appService.createOrderItem(orderItem)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);

        compositeDisposable.add(disposable);
    }

    private void onSuccess(ResponseEntity<Order> orderResponseEntity) {

        if(orderResponseEntity.getStatus().equals("fail")) {
            presenter.onCreatingOrderItemFailure(orderResponseEntity.getMessage());
        } else {
            presenter.onCreatingOrderItemSuccess(orderResponseEntity);
        }
    }

    private void onError(Throwable throwable) {
        presenter.onCreatingOrderItemFailure(throwable.getMessage());
    }

    @Override
    protected void clearDisposable() {
        compositeDisposable.clear();
    }
}
