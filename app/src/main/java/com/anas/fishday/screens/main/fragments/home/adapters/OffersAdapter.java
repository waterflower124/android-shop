package com.anas.fishday.screens.main.fragments.home.adapters;

import android.content.Context;
//import android.databinding.BindingAdapter;
//import android.databinding.DataBindingUtil;
//import android.databinding.ViewDataBinding;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.fishday.BR;
import com.anas.fishday.R;
import com.anas.fishday.entities.Image;
import com.anas.fishday.entities.Product;
import com.anas.fishday.screens.main.fragments.home.interfaces.OnProductClickListener;
import com.anas.fishday.utils.ImageDownloader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anas on 2/16/2018.
 */

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersViewHolder>{
    private Context context;
    private List<Product> offerList = new ArrayList<>();
    private static ImageDownloader imageDownloader;
    private OnProductClickListener onProductClickListener;
    public OffersAdapter(Context context, ImageDownloader downloader, OnProductClickListener listener) {
        this.context = context;
        this.imageDownloader = downloader;
        this.onProductClickListener = listener;
    }

    @Override
    public OffersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewDataBinding viewDataBinding = DataBindingUtil
                .inflate(layoutInflater, R.layout.item_offer, parent, false);
        return new OffersViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(OffersViewHolder holder, int position) {
        Product product = offerList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    public void setOfferList(List<Product> productList) {
        this.offerList.clear();
        this.offerList.addAll(productList);
        notifyDataSetChanged();
    }
//    @BindingAdapter("loadOfferImage")
//    public static void loadOfferImage(ImageView imageView, List<Image> images) {
//        if (images != null && images.size() > 0) {
//            imageDownloader.loadImage(images.get(0).getSmall(), imageView);
//        }
//    }
    class OffersViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding viewDataBinding;
        public OffersViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }

        public void bind(Object obj){
            viewDataBinding.setVariable(BR.product, obj);
        }
    }
}
