package com.anas.fishday.screens.servicedetails;

import android.content.Intent;
//import android.databinding.DataBindingUtil;
import android.net.Uri;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.anas.fishday.BR;
import com.anas.fishday.R;
import com.anas.fishday.base.FishDayActivity;
import com.anas.fishday.databinding.ActivityServiceDetailsBinding;
import com.anas.fishday.entities.Product;
import com.anas.fishday.screens.cartactivity.CartActivity;
import com.anas.fishday.screens.productdetails.adapter.ImagePagerAdapter;
import com.anas.fishday.screens.productdetails.dialog.CreateOrderItemDialogFragment;
import com.anas.fishday.screens.productdetails.interfaces.OnImageClickListener;
import com.anas.fishday.storage.FishDayStorage;
import com.anas.fishday.utils.Constant;

public class ServiceDetailsActivity extends FishDayActivity implements View.OnClickListener,
        OnImageClickListener{

    private ActivityServiceDetailsBinding detailsBinding;
    private Product product;
    private ImagePagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_service_details);

        Bundle bundle = getBundle();
        if (bundle != null) {
            product = (Product) bundle.get(Constant.PRODUCT);
        }
        initToolbar();
        setDataToView();
    }

    private void setDataToView() {
        detailsBinding.setVariable(BR.productDetails, product);
        detailsBinding.setVariable(BR.productDetailsOnClickListener, this);
        detailsBinding.executePendingBindings();

        pagerAdapter = new ImagePagerAdapter(this, product.getImages(), this);
        detailsBinding.imageViewPager.setAdapter(pagerAdapter);
    }
    private void initToolbar() {
        Toolbar toolbar = detailsBinding.toolbar.toolbar;
        detailsBinding.toolbar.toolbar.setTitle(product.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        detailsBinding.toolbar.cartLayout.setVisibility(View.VISIBLE);
        detailsBinding.toolbar.cartLayout.setOnClickListener(view -> startActivity(CartActivity.class));
        try {
            detailsBinding.toolbar.numOfItemsTv.setText(String.valueOf(FishDayStorage.getOrder().getOrderItems().size()));
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contactBtn:
                Uri uri = Uri.parse("whatsapp://send?phone=+966" + getString(R.string.contact_mobile));
                Intent whatsAppIntent = new Intent(Intent.ACTION_VIEW, uri);

                if (whatsAppIntent.resolveActivity(getPackageManager()) != null)
                    startActivity(whatsAppIntent);
                else
                    showSnackBar(detailsBinding.productDetailsCoordinator, "No WhatsApp");
                break;
        }
    }

    @Override
    public void onImageClick(String image) {

    }
}
