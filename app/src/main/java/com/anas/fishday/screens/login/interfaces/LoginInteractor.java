package com.anas.fishday.screens.login.interfaces;

import com.anas.fishday.entities.User;

/**
 * Created by Anas on 2/15/2018.
 */

public interface LoginInteractor {
    void goToMainScreen(User user);
    void showErrorMessage(String message);
}
