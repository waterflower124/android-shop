package com.anas.fishday.screens.orderstatus;

//import android.databinding.DataBindingUtil;
import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.anas.fishday.BR;
import com.anas.fishday.R;
import com.anas.fishday.base.FishDayActivity;
import com.anas.fishday.databinding.ActivityOrderStatusBinding;
import com.anas.fishday.entities.Order;
import com.anas.fishday.functions.lastorder.LastOrderInteractor;
import com.anas.fishday.functions.lastorder.LastOrderPresenter;
import com.anas.fishday.functions.lastorder.LastOrderPresenterImpl;
import com.anas.fishday.storage.FishDayStorage;
import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;

import java.util.HashMap;
import java.util.Map;

public class OrderStatusActivity extends FishDayActivity implements LastOrderInteractor {

    private ActivityOrderStatusBinding binding;
//    private Order order;

    LastOrderPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new LastOrderPresenterImpl(this);

        showProgressDialog();
        presenter.getLastOrder();


    }

    private void initToolbar() {
        Toolbar toolbar = binding.toolbar.toolbar;
//        AppBarLayout appBarLayout = binding.toolbar.appBarLayout;
//        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        binding.toolbar.toolbar.setTitle(R.string.order_status);
        binding.toolbar.cartLayout.setVisibility(View.GONE);
//        appBarLayout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void showErrorOnGettingLastOrder(String message) {
        dismissProgressDialog();
    }

    @Override
    public void handleGettingLastOrder(Order order) {
        dismissProgressDialog();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_status);
        binding.setVariable(BR.order, order);
        binding.orderInfo.setVariable(BR.order, order);

        if(order.getPayment_method().equals("mada")) {
            binding.paymentMethodTextView.setText(R.string.payment_method_mada);
        } else {
            binding.paymentMethodTextView.setText(R.string.payment_method_cash);
        }
        if(order.getPayment_status().equals("pending")) {
            binding.paymentStatusTextView.setText(R.string.payment_status_pending);
        } else if(order.getPayment_status().equals("cancelled")) {
            binding.paymentStatusTextView.setText(R.string.payment_status_cancelled);
        } else {
            binding.paymentStatusTextView.setText(R.string.payment_status_paid);
        }

        initToolbar();

        Map<String, Object> eventValue = new HashMap<String, Object>();
        eventValue.put(AFInAppEventParameterName.REVENUE, order.getTotal());
        eventValue.put(AFInAppEventParameterName.RECEIPT_ID, order.getId());
        eventValue.put("af_order_id", order.getId());
        AppsFlyerLib.getInstance().trackEvent(getApplicationContext(), "completed_purchase", eventValue);
    }
}
