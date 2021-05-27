package com.anas.fishday.screens.register.interfaces;

import com.anas.fishday.base.BasePresenter;
import com.anas.fishday.entities.BaseEntity;
import com.anas.fishday.entities.City;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.entities.User;

import java.util.List;

/**
 * Created by Anas on 2/24/2018.
 */

public interface RegisterPresenter extends BasePresenter{

    void register(User user);
    void onRegisterSuccess(ResponseEntity<User> responseEntity);
    void onRegisterFailure(String message);

    void registerGuest(User user);
    void onRegisterGuestSuccess(ResponseEntity<User> responseEntity);
    void onRegisterGuestFailure(String message);

    void getCities();
    void onGettingCitiesSuccess(ResponseEntity<List<City>> responseEntity);
    void onGettingCitiesFailure(String message);
}
