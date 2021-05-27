package com.anas.fishday.screens.meter;

//import android.databinding.DataBindingUtil;
//import android.support.design.widget.AppBarLayout;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.Toolbar;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.anas.fishday.R;
import com.anas.fishday.base.FishDayActivity;
import com.anas.fishday.databinding.ActivityMeterBinding;

public class MeterActivity extends FishDayActivity {

    ActivityMeterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_meter);
        initToolbar();
        Bundle bundle = getBundle();
        int percentage = bundle.getInt("Meter");
        binding.pointerSpeedometer.speedTo(percentage);

    }

    private void initToolbar() {
        Toolbar toolbar = binding.toolbar.toolbar;
//        AppBarLayout appBarLayout = binding.toolbar.appBarLayout;
        binding.toolbar.toolbar.setTitle(R.string.meter);
//        toolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
//        appBarLayout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
