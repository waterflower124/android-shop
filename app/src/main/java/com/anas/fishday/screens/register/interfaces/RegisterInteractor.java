package com.anas.fishday.screens.register.interfaces;

import com.anas.fishday.entities.City;
import com.anas.fishday.entities.User;

import java.util.List;

/**
 * Created by Anas on 2/24/2018.
 */

public interface RegisterInteractor {
    void goToConfirmationScreen(User user);
    void showErrorMessage(String message);
    void goToMain(User user);

    void viewCities(List<City> cities);

}
