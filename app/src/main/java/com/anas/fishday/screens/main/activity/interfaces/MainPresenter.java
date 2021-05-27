package com.anas.fishday.screens.main.activity.interfaces;

import com.anas.fishday.base.BasePresenter;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.entities.User;

/**
 * Created by Anas on 2/15/2018.
 */

public interface MainPresenter extends BasePresenter{

//    void getLastOrder();
//    void onGettingLastOrderSuccess(ResponseEntity<Order> orderResponseEntity);
//    void onGettingLastOrderFailure(String message);
    void onGettingLogoutSuccess(ResponseEntity<User> orderResponseEntity);
    void onGettingLastLogoutFailure(String message);
    void logout();
}
