package com.anas.fishday.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by Anas on 2/16/2018.
 */

public class JSONUtils {

    public static String loadJSONFromAsset(Context context, int resId) {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(resId);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
