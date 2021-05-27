package com.anas.fishday.utils;

import android.content.Context;
import android.util.Log;

import com.anas.fishday.R;
import com.anas.fishday.entities.ItemMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by Anas on 2/16/2018.
 */

public class MenuUtil {

    public static List<ItemMenu> getMenu(Context context){
        String json = JSONUtils.loadJSONFromAsset(context, R.raw.menu);
        Log.e("JSON ", json);
        Type type = new TypeToken<List<ItemMenu>>(){
        }.getType();
        return new Gson().fromJson(json, type);
    }
}
