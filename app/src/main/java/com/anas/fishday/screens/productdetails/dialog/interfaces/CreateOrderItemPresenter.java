package com.anas.fishday.screens.productdetails.dialog.interfaces;

import com.anas.fishday.base.BasePresenter;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.OrderItem;
import com.anas.fishday.entities.ResponseEntity;

/**
 * Created by Anas on 2/26/2018.
 */

public interface CreateOrderItemPresenter extends BasePresenter {

    void createOrderItem(OrderItem orderItem);
    void onCreatingOrderItemSuccess(ResponseEntity<Order> orderResponseEntity);
    void onCreatingOrderItemFailure(String message);
}
