package com.anas.fishday.screens.main.fragments.about;


//import android.databinding.DataBindingUtil;
import android.content.Context;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.anas.fishday.R;
import com.anas.fishday.base.FishDayFragment;
import com.anas.fishday.databinding.FragmentAboutBinding;
import com.anas.fishday.entities.Order;
import com.anas.fishday.functions.cart.CartGetInteractor;
import com.anas.fishday.functions.cart.CartGetPresenter;
import com.anas.fishday.functions.cart.CartGetPresenterImpl;
import com.anas.fishday.screens.main.activity.MainActivity;
import com.anas.fishday.storage.FishDayStorage;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends FishDayFragment implements CartGetInteractor {


    private FragmentAboutBinding binding;
    private OnFragmentAttachedListener onFragmentAttachedListener;
    private CartGetPresenter presenter;
    public AboutFragment() {
        // Required empty public constructor
    }


    public static AboutFragment newInstance(){
        AboutFragment aboutFragment = new AboutFragment();
        return aboutFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false);
        presenter = new CartGetPresenterImpl(this);
        presenter.getCart();
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentAttachedListener) {
            onFragmentAttachedListener = (OnFragmentAttachedListener) context;
        }
    }

    private void initToolbar(){
        ((MainActivity)getActivity()).toolbar.setTitle(R.string.cart);
    }

    @Override
    public void showErrorOnGettingCart(String message) {

    }

    @Override
    public void handleGettingCart(Order order) {
        FishDayStorage.saveOrder(order);
        onFragmentAttachedListener.updateCartItems(order);
    }

    public interface OnFragmentAttachedListener {
        void updateCartItems(Order order);
    }
}
