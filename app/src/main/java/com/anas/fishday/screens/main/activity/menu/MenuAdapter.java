package com.anas.fishday.screens.main.activity.menu;

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
import com.anas.fishday.entities.ItemMenu;
import com.anas.fishday.screens.main.activity.interfaces.OnMenuSelectedListener;

import java.util.List;

/**
 * Created by Anas on 2/15/2018.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<ItemMenu> itemMenus;
    private Context context;
    private OnMenuSelectedListener onMenuSelectedListener;
    private ImageView imageView;
    public MenuAdapter(List<ItemMenu> itemMenus, Context context, OnMenuSelectedListener listener) {
        this.itemMenus = itemMenus;
        this.context = context;
        this.onMenuSelectedListener = listener;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewDataBinding view = DataBindingUtil.inflate(layoutInflater, R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        ItemMenu itemMenu = itemMenus.get(position);
        holder.bind(itemMenu, onMenuSelectedListener);
    }

    @Override
    public int getItemCount() {
        return itemMenus.size();
    }

    @BindingAdapter({"icon"})
    public static void loadImage(ImageView imageView, int icon) {
        imageView.setImageResource(icon);
    }
    class MenuViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding dataBinding;

        public MenuViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.dataBinding = binding;
        }

        public void bind(Object obj, OnMenuSelectedListener onMenuSelectedListener) {
            dataBinding.setVariable(BR.menu, obj);
            dataBinding.setVariable(BR.onMenuSelectedListener, onMenuSelectedListener);
            dataBinding.executePendingBindings();
        }
    }
}
