package com.anas.fishday.screens.confirmregister;

import android.util.Log;

import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.OrderNew;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.entities.User;
import com.anas.fishday.screens.confirmregister.interfaces.ConfirmRegistrationInteractor;
import com.anas.fishday.screens.confirmregister.interfaces.ConfirmRegistrationPresenter;
import com.anas.fishday.screens.main.fragments.completeorder.interfaces.CompleteOrderPresenter;
import com.google.gson.Gson;

/**
 * Created by Anas on 2/24/2018.
 */

public class ConfirmRegistrationPresenterImpl implements ConfirmRegistrationPresenter {

    private ConfirmRegistrationInteractor interactor;
    private ConfirmRegistrationModel model;

    public ConfirmRegistrationPresenterImpl(ConfirmRegistrationInteractor interactor) {
        this.interactor = interactor;
        model = new ConfirmRegistrationModel(this);
    }

    @Override
    public void confirmRegistration(User user) {
        model.confirmRegistration(user);
    }

    @Override
    public void onConfirmRegistrationSuccess(ResponseEntity<User> responseEntity) {
        String status = responseEntity.getStatus();
        switch (status){
            case "success":
                User user = responseEntity.getEntity();
                interactor.goToHomeScreen(user);
                break;
            case "fail":
                interactor.showErrorMessage(responseEntity.getMessage());
                break;
        }
    }

    @Override
    public void onConfirmRegistrationFailure(String message) {
        interactor.showErrorMessage(message);
    }

    @Override
    public void onStop() {

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
                    interactor.goToOrderStatus(responseEntity.getEntity());
                }
                break;
            case "fail":
//                if (responseEntity.getCode() != null && responseEntity.getCode() == 1){
//                    interactor.goToOrderStatus(responseEntity.getEntity());
//                } else {
//                    interactor.showErrorMessage(responseEntity.getMessage());
//                }
                interactor.showErrorMessage(responseEntity.getMessage());
                break;
        }
    }

    @Override
    public void onCompleteOrderFailed(String message) {
        interactor.showErrorMessage(message);
    }
}
