package com.anas.fishday.screens.main.fragments.services;


import android.content.Context;
import android.content.Intent;
//import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anas.fishday.R;
import com.anas.fishday.base.FishDayActivity;
import com.anas.fishday.base.FishDayFragment;
import com.anas.fishday.databinding.FragmentServicesBinding;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.Product;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.functions.cart.CartGetInteractor;
import com.anas.fishday.functions.cart.CartGetPresenter;
import com.anas.fishday.functions.cart.CartGetPresenterImpl;
import com.anas.fishday.screens.main.fragments.home.interfaces.OnProductClickListener;
import com.anas.fishday.screens.main.fragments.services.adapters.ServicesAdapter;
import com.anas.fishday.screens.main.fragments.services.interfaces.ServicesInteractor;
import com.anas.fishday.screens.main.fragments.services.interfaces.ServicesPresenter;
import com.anas.fishday.screens.servicedetails.ServiceDetailsActivity;
import com.anas.fishday.storage.FishDayStorage;
import com.anas.fishday.utils.Constant;

import java.util.List;

public class ServicesFragment extends FishDayFragment implements ServicesInteractor, CartGetInteractor, OnProductClickListener{

    private RecyclerView.LayoutManager productsLayoutManager;
    private ServicesAdapter servicesAdapter;
    private FragmentServicesBinding binding;

    private ServicesPresenter presenter;
    private CartGetPresenter presenter_cartr;
    private FishDayActivity fishDayActivity;

    private OnFragmentAttachedListener onFragmentAttachedListener;

    public ServicesFragment() {
        // Required empty public constructor
    }


    public static ServicesFragment newInstance() {
        ServicesFragment fragment = new ServicesFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fishDayActivity = (FishDayActivity)getActivity();
        presenter = new ServicesPresenterImpl(this);
        presenter_cartr = new CartGetPresenterImpl(this);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_services, container, false);

        initProductRecyclerView();
        presenter.getServices();
        presenter_cartr.getCart();
        return binding.getRoot();
    }

    private void initProductRecyclerView() {
        servicesAdapter = new ServicesAdapter(fishDayActivity, this);
        productsLayoutManager = new LinearLayoutManager(fishDayActivity);
        binding.servicesRecyclerView.setLayoutManager(productsLayoutManager);
        binding.servicesRecyclerView.setAdapter(servicesAdapter);
    }

    @Override
    public void showErrorForGettingProducts(String message) {
        fishDayActivity.showSnackBar(binding.servicesCoordinator, message);
    }

    @Override
    public void showProducts(ResponseEntity<List<Product>> productResponseEntity) {
        servicesAdapter.setProductList(productResponseEntity.getEntity());
    }

    @Override
    public void onProductClick(Product product) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PRODUCT, product);
        fishDayActivity.startActivity(ServiceDetailsActivity.class, bundle);
    }

    @Override
    public void onAddToCartClick(Product product) {
        Uri uri = Uri.parse("whatsapp://send?phone=+966" + getString(R.string.contact_mobile));
        Intent whatsAppIntent = new Intent(Intent.ACTION_VIEW, uri);

        if (whatsAppIntent.resolveActivity(getActivity().getPackageManager()) != null)
            startActivity(whatsAppIntent);
        else
            fishDayActivity.showSnackBar(binding.servicesCoordinator, "No WhatsApp");
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
