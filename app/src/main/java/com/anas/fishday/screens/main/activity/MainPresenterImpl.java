package com.anas.fishday.screens.main.activity;

import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.entities.User;
import com.anas.fishday.screens.main.activity.interfaces.MainInteractor;
import com.anas.fishday.screens.main.activity.interfaces.MainPresenter;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Anas on 2/15/2018.
 */

public class MainPresenterImpl implements MainPresenter {
    private MainInteractor interactor;
    private MainModel model;


    public MainPresenterImpl(MainInteractor interactor) {
        this.interactor = interactor;
        model = new MainModel(this);

    }

    @Override
    public void onStop() {
        model.clearDisposable();
    }

//    @Override
//    public void getLastOrder(){
//        model.getLastOrder();
//    }
//
//
//    @Override
//    public void onGettingLastOrderSuccess(ResponseEntity<Order> orderResponseEntity) {
//        interactor.handleGettingLastOrder(orderResponseEntity.getEntity());
//    }
//
//    @Override
//    public void onGettingLastOrderFailure(String message) {
//        interactor.showErrorOnGettingLastOrder(message);
//    }

    @Override
    public void logout() {
        model.logout();
    }

    @Override
    public void onGettingLogoutSuccess(ResponseEntity<User> orderResponseEntity) {
        interactor.handleLogout(orderResponseEntity.getEntity());
    }

    @Override
    public void onGettingLastLogoutFailure(String message) {
        interactor.showErrorLogout(message);
    }


}
