package com.anas.fishday.functions.cart;

import com.anas.fishday.base.BasePresenter;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.ResponseEntity;

public interface CartGetPresenter extends BasePresenter {
    void getCart();
    void onGettingCartSuccess(ResponseEntity<Order> orderResponseEntity);
    void onGettingCartError(String message);
}
