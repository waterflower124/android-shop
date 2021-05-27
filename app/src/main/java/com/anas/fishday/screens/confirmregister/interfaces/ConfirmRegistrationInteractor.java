package com.anas.fishday.screens.confirmregister.interfaces;

import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.entities.User;

/**
 * Created by Anas on 2/24/2018.
 */

public interface ConfirmRegistrationInteractor {

    void goToHomeScreen(User user);
    void goToOrderStatus(Order order);
    void showErrorMessage(String message);
    void openPaymentLink(ResponseEntity<Order> responseEntity);
}
