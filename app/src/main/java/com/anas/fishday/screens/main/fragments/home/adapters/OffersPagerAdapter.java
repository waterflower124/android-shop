package com.anas.fishday.screens.main.fragments.home.adapters;

import android.content.Context;
//import android.databinding.DataBindingUtil;
//import android.databinding.ViewDataBinding;
//import android.support.annotation.NonNull;
//import android.support.v4.view.PagerAdapter;
//import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.PagerAdapter;

import com.anas.fishday.BR;
import com.anas.fishday.R;
import com.anas.fishday.entities.Offer;
import com.anas.fishday.entities.Product;
import com.anas.fishday.screens.main.fragments.home.interfaces.OnOfferClickListener;
import com.anas.fishday.screens.main.fragments.home.interfaces.OnProductClickListener;


import java.util.ArrayList;
import java.util.List;

public class OffersPagerAdapter extends PagerAdapter {

    private Context context;
    private List<Offer> offers = new ArrayList<>();
    private OnOfferClickListener onOfferClickListener;

    public OffersPagerAdapter(Context context, OnOfferClickListener listener) {
        this.context = context;
        this.onOfferClickListener = listener;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewDataBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.item_offer, container, false);
        dataBinding.setVariable(BR.offer, offers.get(position));
        dataBinding.setVariable(BR.onOfferClickListener, onOfferClickListener);
        dataBinding.executePendingBindings();
        container.addView(dataBinding.getRoot());
        return dataBinding.getRoot();
    }

    @Override
    public int getCount() {
        return offers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (CardView) object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((CardView) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
        notifyDataSetChanged();
    }
}
