package com.anas.fishday.screens.main.fragments.cart.interfaces;

import com.anas.fishday.base.BasePresenter;
import com.anas.fishday.entities.BaseEntity;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.OrderItem;
import com.anas.fishday.entities.Product;
import com.anas.fishday.entities.RequestEntity;
import com.anas.fishday.entities.ResponseEntity;

import java.util.List;

/**
 * Created by Anas on 2/16/2018.
 */

public interface CartPresenter extends BasePresenter{

//    void getCart();
//    void onGettingCartSucceed(ResponseEntity<Order> cartResponseEntity);
//    void onGettingCartFailed(String message);

    void placeOrder(Order order);
    void onPlacingOrderSucceed(ResponseEntity<Order> cartResponseEntity);
    void onPlacingOrderFailed(String message, ResponseEntity<Order> cartResponseEntity);

    void deleteOrderItem(int orderId);
    void onDeleteOrderItemSucceed(ResponseEntity<Order> cartResponseEntity);
    void onDeleteOrderItemFailed(String message);

    void updateCarItem(Order order);
    void onUpdateItemSucceed(Product product);
    void onUpdateOrderItemFailed(String message);

}
