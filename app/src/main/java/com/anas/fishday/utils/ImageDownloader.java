package com.anas.fishday.utils;

import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Anas on 12/10/2017.
 */

public class ImageDownloader {


    private Picasso picasso;
    public ImageDownloader(Picasso picasso) {
        this.picasso = picasso;
    }

    public void loadImage(String url, ImageView imageView) {
        Picasso.get().load(url).into(imageView);
    }
}
