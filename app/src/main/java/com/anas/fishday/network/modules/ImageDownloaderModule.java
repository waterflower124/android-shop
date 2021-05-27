package com.anas.fishday.network.modules;

import com.anas.fishday.network.ApplicationScope;
import com.anas.fishday.utils.ImageDownloader;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Anas on 12/10/2017.
 */
@Module
public class ImageDownloaderModule {

    @ApplicationScope
    @Provides
    ImageDownloader provideImageDownloader(Picasso picasso){
        return new ImageDownloader(picasso);
    }
}
