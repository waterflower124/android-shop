package com.anas.fishday.screens.confirmregister.interfaces;

import com.anas.fishday.base.BasePresenter;
import com.anas.fishday.entities.AddressResponse;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.OrderNew;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.entities.User;

/**
 * Created by Anas on 2/24/2018.
 */

public interface ConfirmRegistrationPresenter extends BasePresenter{

    void confirmRegistration(User user);
    void onConfirmRegistrationSuccess(ResponseEntity<User> responseEntity);
    void onConfirmRegistrationFailure(String message);

    void completeOrder(OrderNew orderNew);
    void onCompleteOrderSucceed(ResponseEntity<Order> responseEntity);
    void onCompleteOrderFailed(String message);


}
