package com.anas.fishday.screens.main.fragments.completeorder.interfaces;

import com.anas.fishday.base.BasePresenter;
import com.anas.fishday.entities.AddressResponse;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.OrderNew;
import com.anas.fishday.entities.ResponseEntity;

/**
 * Created by Anas on 3/9/2018.
 */

public interface CompleteOrderPresenter extends BasePresenter{

    void getAddress(double lat, double lon);
    void onGettingAddressSucceed(AddressResponse addressResponse);
    void onGettingAddressFailed(String message);

    void completeOrder(OrderNew orderNew);
    void onCompleteOrderSucceed(ResponseEntity<Order> responseEntity);
    void onCompleteOrderFailed(String message);
}
