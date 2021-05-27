package com.anas.fishday.screens.cartactivity;

//import android.databinding.DataBindingUtil;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.anas.fishday.R;
import com.anas.fishday.base.FishDayActivity;
import com.anas.fishday.databinding.ActivityCartBinding;
import com.anas.fishday.screens.main.activity.MainActivity;
import com.anas.fishday.screens.main.fragments.cart.CartFragment;

public class CartActivity extends MainActivity {

    public Toolbar toolbar;
    private ActivityCartBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart);
        initToolbar();
        replaceFragmentBackStack(CartFragment.newInstance());
    }

    private void initToolbar() {
        toolbar = binding.toolbar.toolbar;
        toolbar.setTitle(R.string.cart);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.cartLayout.setVisibility(View.GONE);

    }

}
