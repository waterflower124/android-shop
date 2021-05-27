package com.anas.fishday.screens.productdetails.adapter;

import android.content.Context;
//import android.databinding.BindingAdapter;
//import android.databinding.DataBindingUtil;
//import android.databinding.ViewDataBinding;
//import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.PagerAdapter;

import com.anas.fishday.BR;
import com.anas.fishday.R;
import com.anas.fishday.app.FishDayApplication;
import com.anas.fishday.entities.Image;
import com.anas.fishday.screens.productdetails.interfaces.OnImageClickListener;

import java.util.List;

/**
 * Created by Anas on 2/24/2018.
 */

public class ImagePagerAdapter extends PagerAdapter {

    private Context context;
    private List<Image> images;
    private OnImageClickListener onImageClickListener;

    public ImagePagerAdapter(Context context, List<Image> images, OnImageClickListener onImageClickListener) {
        this.context = context;
        this.images = images;
        this.onImageClickListener = onImageClickListener;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewDataBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.item_image, container, false);
        dataBinding.setVariable(BR.image, images.get(position).getSmall());
        dataBinding.setVariable(BR.onImageClickListener, onImageClickListener);
        dataBinding.executePendingBindings();
        container.addView(dataBinding.getRoot());
        return dataBinding.getRoot();

    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
}
