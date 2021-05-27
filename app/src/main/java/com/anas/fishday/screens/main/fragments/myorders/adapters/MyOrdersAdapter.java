package com.anas.fishday.screens.main.fragments.myorders.adapters;

import android.content.Context;
//import android.databinding.BindingAdapter;
//import android.databinding.DataBindingUtil;
//import android.databinding.ViewDataBinding;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.fishday.BR;
import com.anas.fishday.R;
import com.anas.fishday.databinding.ItemMyOrdersBinding;
import com.anas.fishday.entities.Order;
import com.anas.fishday.screens.main.fragments.myorders.interfaces.OnOrderClickListener;
import com.anas.fishday.utils.DateUtil;
import com.anas.fishday.utils.ImageDownloader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anas on 3/5/2018.
 */

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyOrderViewHolder>{

    private Context context;
    private List<Order> orders = new ArrayList<>();
    private OnOrderClickListener onOrderClickListener;
    private static ImageDownloader imageDownloader;
    public MyOrdersAdapter(Context context, ImageDownloader downloader, OnOrderClickListener listener) {
        this.context = context;
        this.imageDownloader = downloader;
        this.onOrderClickListener = listener;
        orders.clear();
    }

    @Override
    public MyOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemMyOrdersBinding view = DataBindingUtil
                .inflate(layoutInflater, R.layout.item_my_orders, parent, false);
        return new MyOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyOrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.bind(order, onOrderClickListener);
        String date = DateUtil.changeDateFormat(order.getCreatedAt());
        holder.viewDataBinding.orderDate.setText(date);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setOrders(List<Order> orders) {
        this.orders.addAll(orders);
        notifyDataSetChanged();
    }

//    public void updateSingleOrderItem(int index, Order orderItem){
//        orders.set(index, orderItem);
//        notifyItemChanged(index);
//    }

    public List<Order> getOrders(){
        return orders;
    }

    class MyOrderViewHolder extends RecyclerView.ViewHolder{
        ItemMyOrdersBinding viewDataBinding;

        public MyOrderViewHolder(ItemMyOrdersBinding dataBinding) {
            super(dataBinding.getRoot());
            this.viewDataBinding = dataBinding;
        }

        public void bind(Object obj, OnOrderClickListener onOrderItemClickListener) {
            viewDataBinding.setVariable(BR.order, obj);
            viewDataBinding.setVariable(BR.onOrderClickListsner, onOrderItemClickListener);
            viewDataBinding.setVariable(BR.index, getAdapterPosition());
        }

    }
}
