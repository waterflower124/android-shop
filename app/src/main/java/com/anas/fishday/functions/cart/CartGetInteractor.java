package com.anas.fishday.functions.cart;

import com.anas.fishday.entities.Order;

public interface CartGetInteractor {

    void showErrorOnGettingCart(String message);
    void handleGettingCart(Order order);
}
