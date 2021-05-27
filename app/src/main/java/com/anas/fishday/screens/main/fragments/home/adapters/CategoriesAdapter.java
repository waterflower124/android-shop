package com.anas.fishday.screens.main.fragments.home.adapters;

//import android.databinding.DataBindingUtil;
//import android.databinding.ViewDataBinding;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.fishday.R;
import com.anas.fishday.entities.Category;
import com.anas.fishday.screens.main.fragments.home.interfaces.OnCategorySelectedListener;
//import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anas on 2/24/2018.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>{

    private List<Category> categories = new ArrayList<>();
    private OnCategorySelectedListener onCategorySelectedListener;

    public CategoriesAdapter(OnCategorySelectedListener onCategorySelectedListener) {
        this.onCategorySelectedListener = onCategorySelectedListener;
    }

    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_category, parent, false);
        return new CategoriesViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(CategoriesViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category, onCategorySelectedListener);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
    public void setCategories(List<Category> categories) {
        this.categories.clear();
        this.categories.addAll(categories);
        notifyDataSetChanged();
    }
    class CategoriesViewHolder extends RecyclerView.ViewHolder {
        public ViewDataBinding viewDataBinding;
        public CategoriesViewHolder(ViewDataBinding viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }

        public void bind(Object obj, OnCategorySelectedListener onCategorySelectedListener){
            viewDataBinding.setVariable(BR.category, obj);
            viewDataBinding.setVariable(BR.onCategorySelectedListener, onCategorySelectedListener);
        }
    }
}
