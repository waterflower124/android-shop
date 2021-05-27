package com.anas.fishday.storage;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.anas.fishday.utils.Constant;

/**
 * Created by Anas on 2/22/2018.
 */

public class FishDayPreferences {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static FishDayPreferences fishDayPreferences;

    public FishDayPreferences(Application application) {
        sharedPreferences = application.getSharedPreferences("canyo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public static FishDayPreferences getInsatnce(Application application) {
        if (fishDayPreferences == null) {
            fishDayPreferences = new FishDayPreferences(application);
        }
        return fishDayPreferences;
    }


    public static void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }
    public static String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public static boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public static void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }


    public static int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public static void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public static void clear(){
        editor.clear();
        editor.commit();
    }
    public static void remove(String key){
        editor.remove(key);
        editor.commit();
    }

    public static boolean contains(String key){
        return sharedPreferences.contains(key);
    }
}