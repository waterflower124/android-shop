package com.anas.fishday.utils;

//import android.databinding.BindingAdapter;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.anas.fishday.app.FishDayApplication;
import com.anas.fishday.entities.Image;

import java.util.List;

public class ImageUtils {

    @BindingAdapter("loadImage")
    public static void loadImage(ImageView imageView, String image) {
        if (image != null) {
            FishDayApplication.getWebServiceComponent().getImageDownloader().loadImage(image, imageView);
        }
    }

    @BindingAdapter("loadImage")
    public static void loadOfferImage(ImageView imageView, List<Image> images) {
        if (images != null && images.size() > 0) {
            FishDayApplication.getWebServiceComponent().getImageDownloader().loadImage(images.get(0).getSmall(), imageView);
        }
    }
}
