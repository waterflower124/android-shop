package com.anas.fishday.screens.main.fragments.completeorder;

import android.util.Log;

import com.anas.fishday.entities.AddressResponse;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.OrderNew;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.screens.main.fragments.completeorder.interfaces.CompleteOrderInteractor;
import com.anas.fishday.screens.main.fragments.completeorder.interfaces.CompleteOrderPresenter;
import com.google.gson.Gson;

/**
 * Created by Anas on 3/9/2018.
 */

public class CompleteOrderPresenterImpl implements CompleteOrderPresenter {

    private CompleteOrderInteractor interactor;
    private CompleteOrderModel model;

    public CompleteOrderPresenterImpl(CompleteOrderInteractor interactor) {
        this.interactor = interactor;
        model = new CompleteOrderModel(this);
    }

    @Override
    public void onStop() {
        model.clearDisposable();
    }

    @Override
    public void getAddress(double lat, double lon) {
        model.getAddress(lat, lon);
    }

    @Override
    public void onGettingAddressSucceed(AddressResponse addressResponse) {
        if (addressResponse.getStatus().equals("OK")) {
            interactor.showAddress(addressResponse);
        } else {
            interactor.showErrorForGettingAddress();
        }
    }

    @Override
    public void onGettingAddressFailed(String message) {
        interactor.showErrorForGettingAddress();
    }

    @Override
    public void completeOrder(OrderNew orderNew) {
        model.completeOrder(orderNew);
    }

    @Override
    public void onCompleteOrderSucceed(ResponseEntity<Order> responseEntity) {
        String status = responseEntity.getStatus();
        switch (status) {
            case "success":
                String target_url = responseEntity.getTarget_url();
                if(target_url != null && !target_url.isEmpty()) {
                    interactor.openPaymentLink(responseEntity);
                } else {
                    interactor.goToOrderStatus(responseEntity);
                }
                break;
            case "fail":
                if (responseEntity.getCode() != null && responseEntity.getCode() == 2){
                    interactor.goToConfirmationScreen(responseEntity);
                } else {
                    interactor.showErrorForCompletingOrder(responseEntity.getMessage());
                }
                break;
        }
    }

    @Override
    public void onCompleteOrderFailed(String message) {
        interactor.showErrorForCompletingOrder(message);
    }
}
