package com.anas.fishday.network;

import com.anas.fishday.network.modules.AppServiceModule;
import com.anas.fishday.network.modules.ImageDownloaderModule;
import com.anas.fishday.network.modules.PicassoModule;
import com.anas.fishday.utils.ImageDownloader;

import dagger.Component;

/**
 * Created by Anas on 10/1/2017.
 */
@ApplicationScope
@Component(modules = {AppServiceModule.class, PicassoModule.class, ImageDownloaderModule.class})
public interface WebServiceComponent {

    AppService getAppService();
    ImageDownloader getImageDownloader();

}
