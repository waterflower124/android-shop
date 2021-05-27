package com.anas.fishday.screens.main.fragments.cart;

import android.util.Log;

import com.anas.fishday.entities.BaseEntity;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.OrderItem;
import com.anas.fishday.entities.Product;
import com.anas.fishday.entities.RequestEntity;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.screens.main.fragments.cart.interfaces.CartInteractor;
import com.anas.fishday.screens.main.fragments.cart.interfaces.CartPresenter;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Anas on 2/27/2018.
 */

public class CartPresenterImpl implements CartPresenter {


    private CartInteractor interactor;
    private CartModel model;

    public CartPresenterImpl(CartInteractor interactor) {
        this.interactor = interactor;
        model = new CartModel(this);
    }

    @Override
    public void placeOrder(Order order) {
        model.placeOrder(order);
    }

    @Override
    public void onPlacingOrderSucceed(ResponseEntity<Order> cartResponseEntity) {
        Log.d("666666666", new Gson().toJson(cartResponseEntity));
        String status  = cartResponseEntity.getStatus();
        switch (status){
            case "success":
                interactor.showSuccessForPlacingOrder(cartResponseEntity);
                break;
            case "fail":
                interactor.showErrorForPlacingOrder(cartResponseEntity.getMessage(), cartResponseEntity);
                break;
        }

    }

    @Override
    public void onPlacingOrderFailed(String message, ResponseEntity<Order> cartResponseEntity) {
        interactor.showErrorForPlacingOrder(message, cartResponseEntity);
    }

    @Override
    public void deleteOrderItem(int orderItemId) {
        model.deleteOrderItem(orderItemId);
    }

    @Override
    public void onDeleteOrderItemSucceed(ResponseEntity<Order> cartResponseEntity) {
        String status  = cartResponseEntity.getStatus();
        switch (status){
            case "success":
                interactor.showSuccessForDeletingOrderItem(cartResponseEntity);
                break;
            case "fail":
                interactor.showErrorForDeletingOrderItem(cartResponseEntity.getMessage());
                break;
        }
    }

    @Override
    public void onDeleteOrderItemFailed(String message) {
        interactor.showErrorForDeletingOrderItem(message);
    }

    @Override
    public void updateCarItem(Order order) {
        model.updateOrderItem(order);
    }

    @Override
    public void onUpdateItemSucceed(Product product) {
        interactor.showSuccessForUpdateOrderItem(product);
    }

    @Override
    public void onUpdateOrderItemFailed(String message) {
        interactor.showErrorForUpdateOrderItem(message);
    }

    @Override
    public void onStop() {
        model.clearDisposable();
    }
}
