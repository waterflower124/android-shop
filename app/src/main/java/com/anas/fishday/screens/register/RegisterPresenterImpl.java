package com.anas.fishday.screens.register;

import com.anas.fishday.entities.City;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.entities.User;
import com.anas.fishday.screens.register.interfaces.RegisterInteractor;
import com.anas.fishday.screens.register.interfaces.RegisterPresenter;

import java.util.List;

/**
 * Created by Anas on 2/24/2018.
 */

public class RegisterPresenterImpl implements RegisterPresenter {

    private RegisterInteractor interactor;
    private RegisterModel model;

    public RegisterPresenterImpl(RegisterInteractor interactor) {
        this.interactor = interactor;
        model = new RegisterModel(this);
    }

    @Override
    public void register(User user) {
        model.register(user);
    }

    @Override
    public void onRegisterSuccess(ResponseEntity<User> responseEntity) {
        String status = responseEntity.getStatus();
        switch (status) {
            case "success":
                User user = responseEntity.getEntity();
                interactor.goToConfirmationScreen(user);
                break;
            case "fail":
                interactor.showErrorMessage(responseEntity.getMessage());
                break;
        }

    }

    @Override
    public void onRegisterFailure(String message) {
        interactor.showErrorMessage(message);
    }

    @Override
    public void registerGuest(User user) {
        model.registerGuest(user);
    }

    @Override
    public void onRegisterGuestSuccess(ResponseEntity<User> responseEntity) {
        String status = responseEntity.getStatus();
        switch (status) {
            case "success":
                User user = responseEntity.getEntity();
                interactor.goToMain(user);
                break;
            case "fail":
                interactor.showErrorMessage(responseEntity.getMessage());
                break;
        }
    }

    @Override
    public void onRegisterGuestFailure(String message) {
        interactor.showErrorMessage(message);
    }

    @Override
    public void getCities() {
        model.getCities();
    }

    @Override
    public void onGettingCitiesSuccess(ResponseEntity<List<City>> responseEntity) {
        String status = responseEntity.getStatus();
        switch (status) {
            case "success":
                interactor.viewCities(responseEntity.getEntity());
                break;
            case "fail":
                interactor.showErrorMessage(responseEntity.getMessage());
                break;
        }
    }

    @Override
    public void onGettingCitiesFailure(String message) {
        interactor.showErrorMessage(message);
    }

    @Override
    public void onStop() {
        model.clearDisposable();
    }
}
