package com.anas.fishday.screens;

//import android.databinding.DataBindingUtil;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.anas.fishday.BR;
import com.anas.fishday.R;
import com.anas.fishday.base.FishDayActivity;
import com.anas.fishday.databinding.ActivityImageViewerBinding;

public class ImageViewerActivity extends FishDayActivity {

    private ActivityImageViewerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_viewer);
        binding.setVariable(BR.image, getBundle().getString("image"));
        binding.executePendingBindings();
    }
}
