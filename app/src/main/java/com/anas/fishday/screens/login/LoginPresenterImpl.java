package com.anas.fishday.screens.login;

import android.util.Log;

import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.entities.User;
import com.anas.fishday.screens.login.interfaces.LoginInteractor;
import com.anas.fishday.screens.login.interfaces.LoginPresenter;

/**
 * Created by Anas on 2/15/2018.
 */

public class LoginPresenterImpl implements LoginPresenter {

    private LoginInteractor interactor;
    private LoginModel model;
    public LoginPresenterImpl(LoginInteractor interactor) {
        this.interactor = interactor;
        model = new LoginModel(this);
    }

    @Override
    public void login(User user) {
        model.login(user);
    }

    @Override
    public void onLoginSuccess(ResponseEntity<User> responseEntity) {
        String status = responseEntity.getStatus();
        Log.d("555555555", status);
        switch (status){
            case "success":
                User user = responseEntity.getEntity();
                interactor.goToMainScreen(user);
                break;
            case "fail":
                interactor.showErrorMessage(responseEntity.getMessage());
                break;
        }

    }

    @Override
    public void onLoginFailure(String message) {
        interactor.showErrorMessage(message);
    }

    @Override
    public void onStop() {
        model.clearDisposable();
    }
}
