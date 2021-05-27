package com.anas.fishday.screens.main.fragments.cart.interfaces;

import com.anas.fishday.entities.BaseEntity;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.Product;
import com.anas.fishday.entities.ResponseEntity;

/**
 * Created by Anas on 2/16/2018.
 */

public interface CartInteractor {

//    void showErrorForGettingCart(String message);
//    void showCart(ResponseEntity<Order> cartResponseEntity);

    void showErrorForPlacingOrder(String message, ResponseEntity<Order> cartResponseEntity);
    void showSuccessForPlacingOrder(ResponseEntity<Order> cartResponseEntity);

    void showErrorForDeletingOrderItem(String message);
    void showSuccessForDeletingOrderItem(ResponseEntity<Order> cartResponseEntity);

    void showErrorForUpdateOrderItem(String message);
    void showSuccessForUpdateOrderItem(Product product);
}
