package com.anas.fishday.screens.productdetails.dialog.interfaces;

import com.anas.fishday.entities.Product;

/**
 * Created by Anas on 2/26/2018.
 */

public interface OnCreateOrderDialogClickListener {

    void onCancelClick();
    void onAddToCartClick(Product product);
    void onIncreaseQuantityClick();
    void onDecreaseQuantityClick();
}
