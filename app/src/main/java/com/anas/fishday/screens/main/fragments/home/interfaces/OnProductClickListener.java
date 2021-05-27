package com.anas.fishday.screens.main.fragments.home.interfaces;

import com.anas.fishday.entities.Product;

/**
 * Created by Anas on 2/23/2018.
 */

public interface OnProductClickListener {
    void onProductClick(Product product);
    void onAddToCartClick(Product product);
}
