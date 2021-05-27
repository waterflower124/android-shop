package com.anas.fishday.functions.lastorder;

import com.anas.fishday.app.FishDayApplication;
import com.anas.fishday.base.BaseModel;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.network.AppService;
import com.anas.fishday.screens.main.activity.interfaces.MainPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LastOrderModel extends BaseModel {

    private LastOrderPresenter presenter;
    private CompositeDisposable compositeDisposable;
    private AppService appService;
    public LastOrderModel(LastOrderPresenter presenter) {

        this.presenter = presenter;
        compositeDisposable = new CompositeDisposable();
        initAppService();
    }

    private void initAppService() {
        appService = FishDayApplication.getWebServiceComponent().getAppService();
    }

    public void getLastOrder(){
        Disposable disposable = appService.getLastOrder()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);

        compositeDisposable.add(disposable);
    }

    private void onSuccess(ResponseEntity<Order> orderResponseEntity) {
//        Log.d("4444444444", new Gson().toJson(orderResponseEntity));
        presenter.onGettingLastOrderSuccess(orderResponseEntity);
    }

    private void onError(Throwable throwable) {
        presenter.onGettingLastOrderFailure(throwable.getMessage());
    }

    @Override
    protected void clearDisposable() {
        compositeDisposable.clear();
    }
}
