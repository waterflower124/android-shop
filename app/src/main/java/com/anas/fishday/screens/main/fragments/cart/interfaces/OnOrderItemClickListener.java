package com.anas.fishday.screens.main.fragments.cart.interfaces;

import com.anas.fishday.entities.OrderItem;

/**
 * Created by Anas on 2/27/2018.
 */

public interface OnOrderItemClickListener {

    void onIncreaseQuantityClick(OrderItem orderItem, int index);
    void onDecreaseQuantityClick(OrderItem orderItem, int index);
    void onDeleteItemClick(OrderItem orderItem, int index);

}
