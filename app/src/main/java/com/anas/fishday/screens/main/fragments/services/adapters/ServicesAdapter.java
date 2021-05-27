package com.anas.fishday.screens.main.fragments.services.adapters;

import android.content.Context;
//import android.databinding.DataBindingUtil;
//import android.databinding.ViewDataBinding;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.fishday.BR;
import com.anas.fishday.R;
import com.anas.fishday.entities.Product;
import com.anas.fishday.screens.main.fragments.home.interfaces.OnProductClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anas on 2/16/2018.
 */

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ProductsViewHolder> {
    private Context context;
    private List<Product> productList = new ArrayList<>();
    private OnProductClickListener onProductClickListener;

    public ServicesAdapter(Context context, OnProductClickListener listener) {
        this.context = context;
        this.onProductClickListener = listener;
        productList.clear();
    }

    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewDataBinding view = DataBindingUtil
                .inflate(layoutInflater, R.layout.item_service, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductsViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product, onProductClickListener);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void setProductList(List<Product> productList) {
        this.productList.clear();
        this.productList.addAll(productList);
        notifyDataSetChanged();
    }

    class ProductsViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding viewDataBinding;

        public ProductsViewHolder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            this.viewDataBinding = dataBinding;
        }

        public void bind(Object obj, OnProductClickListener onProductClickListener) {
            viewDataBinding.setVariable(BR.product, obj);
            viewDataBinding.setVariable(BR.onProductClickListener, onProductClickListener);
        }

    }
}