package com.anas.fishday.app;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
//import android.support.multidex.MultiDexApplication;
import androidx.multidex.MultiDexApplication;

import com.anas.fishday.network.DaggerWebServiceComponent;
import com.anas.fishday.network.WebServiceComponent;
import com.anas.fishday.storage.FishDayPreferences;
import com.anas.fishday.storage.FishDayStorage;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;

/**
 * Created by Anas on 2/15/2018.
 */

public class FishDayApplication extends MultiDexApplication {

    private static Context context;
    private static WebServiceComponent webServiceComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        webServiceComponent = DaggerWebServiceComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        FishDayPreferences.getInsatnce(this);


    }

    public static Context getContext(){
        return context;
    }

    public static WebServiceComponent getWebServiceComponent() {
        return webServiceComponent;
    }
}
