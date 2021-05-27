package com.anas.fishday.screens.main.fragments.myorders;

import com.anas.fishday.base.BasePresenter;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.screens.main.fragments.myorders.interfaces.MyOrdersInteractor;
import com.anas.fishday.screens.main.fragments.myorders.interfaces.MyOrdersPresenter;

import java.util.List;

/**
 * Created by Anas on 3/5/2018.
 */

public class MyOrdersPresenterImpl implements MyOrdersPresenter {

    private MyOrdersInteractor interactor;
    private MyOrdersModel model;

    public MyOrdersPresenterImpl(MyOrdersInteractor interactor) {
        this.interactor = interactor;
        model = new MyOrdersModel(this);
    }
    @Override
    public void getMyOrders() {
        model.getMyOrders();
    }

    @Override
    public void onGettingMyOrdersSucceed(ResponseEntity<List<Order>> myOrderResponseEntity) {
        String status  = myOrderResponseEntity.getStatus();
        switch (status){
            case "success":
                interactor.showMyOrders(myOrderResponseEntity);
                break;
            case "fail":
                interactor.showErrorForGettingMyOrders(myOrderResponseEntity.getMessage());
                break;
        }
    }

    @Override
    public void onGettingMyOrdersFailed(String message) {
        interactor.showErrorForGettingMyOrders(message);
    }

    @Override
    public void onStop() {
        model.clearDisposable();
    }
}
