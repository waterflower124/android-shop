package com.anas.fishday.functions.lastorder;

import com.anas.fishday.entities.Order;

public interface LastOrderInteractor {

    void showErrorOnGettingLastOrder(String message);
    void handleGettingLastOrder(Order order);
}
