package com.anas.fishday.screens.main.fragments.myorders;


//import android.databinding.DataBindingUtil;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.fishday.R;
import com.anas.fishday.app.FishDayApplication;
import com.anas.fishday.base.FishDayActivity;
import com.anas.fishday.base.FishDayFragment;
import com.anas.fishday.databinding.FragmentMyOrdersBinding;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.functions.cart.CartGetInteractor;
import com.anas.fishday.functions.cart.CartGetPresenter;
import com.anas.fishday.functions.cart.CartGetPresenterImpl;
import com.anas.fishday.screens.main.fragments.myorders.adapters.MyOrdersAdapter;
import com.anas.fishday.screens.main.fragments.myorders.interfaces.MyOrdersInteractor;
import com.anas.fishday.screens.main.fragments.myorders.interfaces.MyOrdersPresenter;
import com.anas.fishday.screens.main.fragments.myorders.interfaces.OnOrderClickListener;
import com.anas.fishday.storage.FishDayStorage;
import com.anas.fishday.utils.ImageDownloader;

import java.util.List;

public class MyOrdersFragment extends FishDayFragment implements MyOrdersInteractor, CartGetInteractor, OnOrderClickListener {

    private FragmentMyOrdersBinding binding;
    private FishDayActivity fishDayActivity;
    private MyOrdersPresenter presenter;
    private CartGetPresenter presenter_cart;
    private MyOrdersAdapter myOrdersAdapter;
    private ImageDownloader imageDownloader;
    private RecyclerView.LayoutManager layoutManager;

    private OnFragmentAttachedListener onFragmentAttachedListener;


    public MyOrdersFragment() {

    }

    public static MyOrdersFragment newInstance() {
        MyOrdersFragment fragment = new MyOrdersFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fishDayActivity = (FishDayActivity) getActivity();
        presenter = new MyOrdersPresenterImpl(this);
        presenter_cart = new CartGetPresenterImpl(this);
        imageDownloader = FishDayApplication.getWebServiceComponent().getImageDownloader();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_orders, container, false);
        initMyOrdersRecyclerView();
        getMyOrders();
        presenter_cart.getCart();
        return binding.getRoot();
    }

    private void getMyOrders() {
        fishDayActivity.showProgressDialog();
        presenter.getMyOrders();
    }
    private void initMyOrdersRecyclerView() {
        myOrdersAdapter = new MyOrdersAdapter(fishDayActivity, imageDownloader, this);
        layoutManager = new LinearLayoutManager(fishDayActivity);
        binding.myOrderRecyclerView.setLayoutManager(layoutManager);
        binding.myOrderRecyclerView.setAdapter(myOrdersAdapter);
    }
    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void showErrorForGettingMyOrders(String message) {
        fishDayActivity.dismissProgressDialog();
        fishDayActivity.showSnackBar(binding.myOrdersCoordinator, message);
    }

    @Override
    public void showMyOrders(ResponseEntity<List<Order>> myOrdersResponseEntity) {
        myOrdersAdapter.setOrders(myOrdersResponseEntity.getEntity());
        fishDayActivity.dismissProgressDialog();
    }

    @Override
    public void showErrorOnGettingCart(String message) {

    }

    @Override
    public void handleGettingCart(Order order) {
        FishDayStorage.saveOrder(order);
        onFragmentAttachedListener.updateCartItems(order);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentAttachedListener) {
            onFragmentAttachedListener = (OnFragmentAttachedListener) context;
        }
    }

    public interface OnFragmentAttachedListener {
        void updateCartItems(Order order);
    }
}
