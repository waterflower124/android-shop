package com.anas.fishday.screens.main.fragments.myorders.interfaces;

import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.ResponseEntity;

import java.util.List;

/**
 * Created by Anas on 2/16/2018.
 */

public interface MyOrdersInteractor {

    void showErrorForGettingMyOrders(String message);
    void showMyOrders(ResponseEntity<List<Order>> myOrdersResponseEntity);

}
