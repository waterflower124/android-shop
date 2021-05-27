package com.anas.fishday.screens.main.fragments.completeorder.interfaces;

import com.anas.fishday.entities.AddressResponse;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.ResponseEntity;

/**
 * Created by Anas on 3/9/2018.
 */

public interface CompleteOrderInteractor {

    void showErrorForGettingAddress();
    void showAddress(AddressResponse addressResponse);

    void showErrorForCompletingOrder(String message);
    void goToOrderStatus(ResponseEntity<Order> responseEntity);
    void goToConfirmationScreen(ResponseEntity<Order> responseEntity);
    void openPaymentLink(ResponseEntity<Order> responseEntity);
}
