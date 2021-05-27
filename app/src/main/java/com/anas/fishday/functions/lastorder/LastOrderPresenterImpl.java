package com.anas.fishday.functions.lastorder;

import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.screens.main.activity.MainModel;
import com.anas.fishday.screens.main.activity.interfaces.MainInteractor;

public class LastOrderPresenterImpl implements LastOrderPresenter {

    private LastOrderInteractor interactor;
    private LastOrderModel model;


    public LastOrderPresenterImpl(LastOrderInteractor interactor) {
        this.interactor = interactor;
        model = new LastOrderModel(this);

    }

    @Override
    public void onStop() {
        model.clearDisposable();
    }

    @Override
    public void getLastOrder(){
        model.getLastOrder();
    }


    @Override
    public void onGettingLastOrderSuccess(ResponseEntity<Order> orderResponseEntity) {
        interactor.handleGettingLastOrder(orderResponseEntity.getEntity());
    }

    @Override
    public void onGettingLastOrderFailure(String message) {
        interactor.showErrorOnGettingLastOrder(message);
    }


}
