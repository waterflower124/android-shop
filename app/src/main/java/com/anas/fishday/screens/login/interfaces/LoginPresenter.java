package com.anas.fishday.screens.login.interfaces;

import com.anas.fishday.base.BasePresenter;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.entities.User;

/**
 * Created by Anas on 2/15/2018.
 */

public interface LoginPresenter extends BasePresenter {

    void login(User user);
    void onLoginSuccess(ResponseEntity<User> responseEntity);
    void onLoginFailure(String message);
}
