package com.anas.fishday.app;

import android.app.Application;

import com.anas.fishday.network.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Anas on 10/1/2017.
 */
@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }


    @Provides
    @ApplicationScope
    Application getApplication(){
        return application;
    }
}
