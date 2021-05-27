package com.anas.fishday.screens.splash;

import android.content.Intent;
import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anas.fishday.R;
import com.anas.fishday.base.FishDayActivity;
import com.anas.fishday.screens.login.LoginActivity;
import com.anas.fishday.screens.main.activity.MainActivity;
import com.anas.fishday.screens.register.RegisterActivity;
import com.anas.fishday.storage.FishDayStorage;
import com.appsflyer.AppsFlyerLib;

public class SplashActivity extends FishDayActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppsFlyerLib.getInstance().startTracking(this.getApplication(), "ScnTDN86sExUEQJotbygy8");
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (FishDayStorage.getUser() != null) {
                startActivity(MainActivity.class, true);
            } else {
                startActivity(RegisterActivity.class, true);
            }

        }, 2000);
    }
}
