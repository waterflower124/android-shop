package com.anas.fishday.screens.main.fragments.cart.adapters;

import android.content.Context;
//import android.databinding.BindingAdapter;
//import android.databinding.DataBindingUtil;
//import android.databinding.ViewDataBinding;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.fishday.BR;
import com.anas.fishday.R;
import com.anas.fishday.entities.Image;
import com.anas.fishday.entities.OrderItem;
import com.anas.fishday.screens.main.fragments.cart.interfaces.OnOrderItemClickListener;
import com.anas.fishday.utils.ImageDownloader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anas on 2/16/2018.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<OrderItem> orderItems = new ArrayList<>();
    private OnOrderItemClickListener onOrderItemClickListener;
    private static ImageDownloader imageDownloader;

    public CartAdapter(Context context, ImageDownloader downloader, OnOrderItemClickListener listener) {
        this.context = context;
        this.imageDownloader = downloader;
        this.onOrderItemClickListener = listener;
        orderItems.clear();
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewDataBinding view = DataBindingUtil
                .inflate(layoutInflater, R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        OrderItem product = orderItems.get(position);
        holder.bind(product, onOrderItemClickListener);
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public void setOrderItems(List<OrderItem> productList) {
        this.orderItems.addAll(productList);
        notifyDataSetChanged();
    }

    public void removeOrderItem(int index) {
        this.orderItems.remove(index);
        this.notifyDataSetChanged();
    }

    public void updateSingleOrderItem(int index, OrderItem orderItem) {
        orderItems.set(index, orderItem);
        notifyItemChanged(index);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    @BindingAdapter("loadOrderItemImage")
    public static void loadOrderItemImage(ImageView imageView, List<Image> images) {
        if (images != null && images.size() > 0) {
            imageDownloader.loadImage(images.get(0).getSmall(), imageView);
        }
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding viewDataBinding;

        public CartViewHolder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            this.viewDataBinding = dataBinding;
        }

        public void bind(Object obj, OnOrderItemClickListener onOrderItemClickListener) {
            viewDataBinding.setVariable(BR.orderItem, obj);
            viewDataBinding.setVariable(BR.onOrderItemClickListener, onOrderItemClickListener);
            viewDataBinding.setVariable(BR.index, getAdapterPosition());
        }

    }
}
