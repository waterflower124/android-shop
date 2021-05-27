package com.anas.fishday.screens.main.fragments.myorders.interfaces;

import com.anas.fishday.base.BasePresenter;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.ResponseEntity;

import java.util.List;

/**
 * Created by Anas on 2/16/2018.
 */

public interface MyOrdersPresenter extends BasePresenter{

    void getMyOrders();
    void onGettingMyOrdersSucceed(ResponseEntity<List<Order>> myOrderResponseEntity);
    void onGettingMyOrdersFailed(String message);
}
