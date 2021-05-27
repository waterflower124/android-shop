package com.anas.fishday.functions.lastorder;

import com.anas.fishday.base.BasePresenter;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.ResponseEntity;

public interface LastOrderPresenter extends BasePresenter {

    void getLastOrder();
    void onGettingLastOrderSuccess(ResponseEntity<Order> orderResponseEntity);
    void onGettingLastOrderFailure(String message);
}
