package com.anas.fishday.system;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;


/**
 * Created by Anas on 9/28/2017.
 */

public class SystemPermissions {

    private Activity activity;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public SystemPermissions(Activity activity) {
        this.activity = activity;
    }

    public boolean checkPermission(String permission){
        if (ContextCompat.checkSelfPermission(activity.getBaseContext(), permission)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }
    public  void requestPermission(String permission, int requestCode){
        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
    }

    public boolean requestSystemPermission(String permission, int requestCode){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity.getBaseContext(),
                    permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{permission},
                        requestCode);
                return false;
            } else {
                return true;
            }
        }else {
            return true;
        }
    }
}
