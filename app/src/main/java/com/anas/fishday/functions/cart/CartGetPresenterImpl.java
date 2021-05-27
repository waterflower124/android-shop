package com.anas.fishday.functions.cart;

import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.ResponseEntity;

public class CartGetPresenterImpl implements CartGetPresenter {

    private CartGetInteractor interactor;
    private CartGetModel model;

    public CartGetPresenterImpl(CartGetInteractor interactor) {
        this.interactor = interactor;
        model = new CartGetModel(this);

    }

    @Override
    public void getCart() {
        model.getCart();
    }

    @Override
    public void onGettingCartSuccess(ResponseEntity<Order> orderResponseEntity) {
        interactor.handleGettingCart(orderResponseEntity.getEntity());
    }

    @Override
    public void onGettingCartError(String message) {
        interactor.showErrorOnGettingCart(message);
    }

    @Override
    public void onStop() {
        model.clearDisposable();
    }
}
