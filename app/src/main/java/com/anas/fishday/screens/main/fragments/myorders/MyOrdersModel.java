package com.anas.fishday.screens.main.fragments.myorders;

import com.anas.fishday.app.FishDayApplication;
import com.anas.fishday.base.BaseModel;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.network.AppService;
import com.anas.fishday.screens.main.fragments.cart.interfaces.CartPresenter;
import com.anas.fishday.screens.main.fragments.myorders.interfaces.MyOrdersPresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Anas on 3/5/2018.
 */

public class MyOrdersModel extends BaseModel {

    private AppService appService;
    private MyOrdersPresenter presenter;
    private CompositeDisposable disposable;

    public MyOrdersModel(MyOrdersPresenter presenter) {
        this.presenter = presenter;
        initApiCall();
        disposable = new CompositeDisposable();
    }

    private void initApiCall() {
        appService = FishDayApplication.getWebServiceComponent().getAppService();
    }

    public void getMyOrders() {
        Disposable productsDisposable = appService.getMyOrders()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRequestMyOrdersSuccess, this::onRequestMyOrdersError);
        disposable.add(productsDisposable);
    }

    private void onRequestMyOrdersSuccess(ResponseEntity<List<Order>> responseEntity) {
        presenter.onGettingMyOrdersSucceed(responseEntity);
    }
    private void onRequestMyOrdersError(Throwable throwable) {
        presenter.onGettingMyOrdersFailed(throwable.getMessage());
    }

    @Override
    protected void clearDisposable() {
        disposable.clear();
    }
}
