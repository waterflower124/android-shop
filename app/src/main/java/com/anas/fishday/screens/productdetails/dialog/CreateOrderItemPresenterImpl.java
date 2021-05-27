package com.anas.fishday.screens.productdetails.dialog;

import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.OrderItem;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.screens.productdetails.dialog.interfaces.CreateOrderItemInteractor;
import com.anas.fishday.screens.productdetails.dialog.interfaces.CreateOrderItemPresenter;

/**
 * Created by Anas on 2/26/2018.
 */

public class CreateOrderItemPresenterImpl implements CreateOrderItemPresenter {
    private CreateOrderItemInteractor interactor;
    private CreateOrderItemModel model;

    public CreateOrderItemPresenterImpl(CreateOrderItemInteractor interactor) {
        this.interactor = interactor;
        model = new CreateOrderItemModel(this);
    }

    @Override
    public void onStop() {
        model.clearDisposable();
    }

    @Override
    public void createOrderItem(OrderItem orderItem) {
        model.createOrderItem(orderItem);
    }

    @Override
    public void onCreatingOrderItemSuccess(ResponseEntity<Order> orderResponseEntity) {
        interactor.onCreateOrderItemSuccess(orderResponseEntity.getEntity());
    }

    @Override
    public void onCreatingOrderItemFailure(String message) {
        interactor.onCreateOrderItemFailure(message);
    }
}
