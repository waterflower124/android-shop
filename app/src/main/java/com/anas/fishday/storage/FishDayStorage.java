package com.anas.fishday.storage;

import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.OrderNew;
import com.anas.fishday.entities.User;
import com.anas.fishday.utils.Constant;
import com.anas.fishday.utils.DateUtil;
import com.google.gson.Gson;

/**
 * Created by Anas on 2/15/2018.
 */

public class FishDayStorage {

    public static void saveDeviceToken(String token) {
        FishDayPreferences.putString(Constant.DEVICE_TOKEN, token);
    }
    public static String getDeviceToken() {
        return FishDayPreferences.getString(Constant.DEVICE_TOKEN);
    }

    public static void setOrderId(int orderId){
        FishDayPreferences.putInt(Constant.ORDER_ID, orderId);
    }
    public static int getOrderId(){
        return FishDayPreferences.getInt(Constant.ORDER_ID);
    }

    public static void setUser(User user){
        String userJson = new Gson().toJson(user);
        FishDayPreferences.putString(Constant.USER, userJson);
    }

    public static User getUser(){
        String userJson = FishDayPreferences.getString(Constant.USER);
        return new Gson().fromJson(userJson, User.class);
    }

    public static void saveOrder(Order order){
//        String date = DateUtil.changeDateFormat(order.getCreatedAt());
//        order.setCreatedAt(date);
        String orderJson = new Gson().toJson(order);
        FishDayPreferences.putString(Constant.ORDER, orderJson);
    }

    public static Order getOrder(){
        String orderJson = FishDayPreferences.getString(Constant.ORDER);
        return new Gson().fromJson(orderJson, Order.class);
    }

    public static void saveOrderNew(OrderNew ordernew){
//        String date = DateUtil.changeDateFormat(order.getCreatedAt());
//        order.setCreatedAt(date);
        String orderJson = new Gson().toJson(ordernew);
        FishDayPreferences.putString(Constant.ORDER, orderJson);
    }

    public static OrderNew getOrderNew(){
        String orderJson = FishDayPreferences.getString(Constant.ORDER);
        return new Gson().fromJson(orderJson, OrderNew.class);
    }

    public static void setAppLanguage(String language) {
        FishDayPreferences.putString("language", language);
    }

    public static String getAppLanguage() {
        String language;
        if (FishDayPreferences.contains("language")) {
            language = FishDayPreferences.getString("language");
        }else {
            language = Constant.LANGUAGE_EN;
        }
        return language;
    }

    public static void setMeterPercentage(int meterPercentage) {
        FishDayPreferences.putInt(Constant.METER_PERCENTAGE, meterPercentage);
    }

    public static int getMeterPercentage() {
        return FishDayPreferences.getInt(Constant.METER_PERCENTAGE);
    }

    public static void removeUser(){
        FishDayPreferences.remove(Constant.USER);
    }
    public static void removeOrder(){
        FishDayPreferences.remove(Constant.ORDER);
    }
    public static void clearData(){
        FishDayPreferences.clear();
    }
}
