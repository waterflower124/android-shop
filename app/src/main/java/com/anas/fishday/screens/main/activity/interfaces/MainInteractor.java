package com.anas.fishday.screens.main.activity.interfaces;

import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.User;

/**
 * Created by Anas on 2/15/2018.
 */

public interface MainInteractor {

//    void showErrorOnGettingLastOrder(String message);
//    void handleGettingLastOrder(Order order);

    void handleLogout(User user);
    void showErrorLogout(String message);
}
