package com.anas.fishday.screens.confirmregister;

import android.util.Log;

import com.anas.fishday.app.FishDayApplication;
import com.anas.fishday.base.BaseModel;
import com.anas.fishday.entities.BaseEntity;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.OrderNew;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.entities.User;
import com.anas.fishday.network.AppService;
import com.anas.fishday.screens.confirmregister.interfaces.ConfirmRegistrationPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Anas on 2/24/2018.
 */

public class ConfirmRegistrationModel extends BaseModel {
    private ConfirmRegistrationPresenter presenter;
    private AppService appService;
    private CompositeDisposable compositeDisposable;

    public ConfirmRegistrationModel(ConfirmRegistrationPresenter presenter) {
        this.presenter = presenter;
        initApiCall();
        compositeDisposable = new CompositeDisposable();
    }

    private void initApiCall() {
        appService = FishDayApplication.getWebServiceComponent().getAppService();
    }

    public void confirmRegistration(User user) {
        Disposable disposable = appService.confirmRegistration(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onConfirmRegistrationSuccess, this::onConfirmRegistrationFailure);

        compositeDisposable.add(disposable);
    }

    private void onConfirmRegistrationSuccess(ResponseEntity<User> responseEntity) {
        presenter.onConfirmRegistrationSuccess(responseEntity);
    }

    private void onConfirmRegistrationFailure(Throwable throwable) {
        presenter.onConfirmRegistrationFailure(throwable.getMessage());
    }

    public void completeOrder(OrderNew orderNew) {
        Disposable completeOrderDisposable = appService.completeOrder(orderNew)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::OnCompletingOrderSuccess, this::onCompletingOrderError);

        compositeDisposable.add(completeOrderDisposable);
    }

    private void onCompletingOrderError(Throwable throwable) {
        presenter.onCompleteOrderFailed(throwable.getMessage());
    }

    private void OnCompletingOrderSuccess(ResponseEntity<Order> responseEntity) {

        presenter.onCompleteOrderSucceed(responseEntity);
    }

    @Override
    protected void clearDisposable() {
        compositeDisposable.clear();
    }
}
