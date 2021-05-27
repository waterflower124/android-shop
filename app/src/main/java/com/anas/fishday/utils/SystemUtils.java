package com.anas.fishday.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * Created by Anas on 2/24/2018.
 */

public class SystemUtils {

    @SuppressLint("MissingPermission")
    public static String getMobileNumberFromSystem(Context context){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number();
    }
}
