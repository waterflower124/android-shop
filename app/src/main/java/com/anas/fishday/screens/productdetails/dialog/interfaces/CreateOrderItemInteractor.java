package com.anas.fishday.screens.productdetails.dialog.interfaces;

import com.anas.fishday.entities.Order;

/**
 * Created by Anas on 2/26/2018.
 */

public interface CreateOrderItemInteractor {

    void onCreateOrderItemFailure(String message);
    void onCreateOrderItemSuccess(Order order);
}
