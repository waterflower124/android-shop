package com.anas.fishday.screens.confirmregister;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
//import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
//import android.support.annotation.NonNull;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.anas.fishday.BR;
import com.anas.fishday.R;
import com.anas.fishday.base.FishDayActivity;
import com.anas.fishday.databinding.ActivityConfirmationBinding;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.OrderNew;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.entities.User;
import com.anas.fishday.screens.confirmregister.interfaces.ConfirmRegistrationInteractor;
import com.anas.fishday.screens.confirmregister.interfaces.ConfirmRegistrationPresenter;
import com.anas.fishday.screens.main.activity.MainActivity;
import com.anas.fishday.screens.orderstatus.OrderStatusActivity;
import com.anas.fishday.storage.FishDayStorage;
import com.anas.fishday.system.SystemPermissions;
import com.anas.fishday.utils.Constant;
import com.anas.fishday.utils.Utils;

public class ConfirmationActivity extends FishDayActivity implements ConfirmRegistrationInteractor, View.OnClickListener{

    private ActivityConfirmationBinding binding;
    private ConfirmRegistrationPresenter presenter;
    private String mobileNumber;
    private int payment_visa;
    private String target_url;
    private String confirmationCode;
    private boolean isGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirmation);
        binding.setVariable(BR.onClickListener, this);
        presenter = new ConfirmRegistrationPresenterImpl(this);
        Bundle bundle = getBundle();
        mobileNumber = bundle.getString(Constant.MOBILE_NUMBER);
        payment_visa = bundle.getInt("payment_visa");
        target_url = bundle.getString("target_url");
        isGuest = getBundle().getBoolean(Constant.IS_GUEST);

        confirmationCode = bundle.getString(Constant.CONFIRMATION_CODE);
//        binding.confirmationCodeTv.setText(confirmationCode);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    private void confirmRegistration() {
        showProgressDialog();
        Utils.hideKeyboard(getCurrentFocus(), this);
        confirmationCode = binding.otpView.getOTP();
        if(!isGuest) { // when registration
            User user = new User();
            user.setMobileNumber(mobileNumber);
            user.setConfirmationCode(confirmationCode);
            presenter.confirmRegistration(user);
        } else { // when complete order
            OrderNew orderNew = FishDayStorage.getOrderNew();
            orderNew.setVerification_code(confirmationCode);
            presenter.completeOrder(orderNew);
        }
    }

    private boolean isValidInput() {
        if (!binding.otpView.getOTP().isEmpty() &&
                !mobileNumber.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirmAccountBtn:
                if (isValidInput()) {
                    confirmRegistration();
                }
                break;
        }
    }

    @Override
    public void goToHomeScreen(User user) {
        FishDayStorage.setUser(user);
        dismissProgressDialog();
        if (isGuest) {
            finish();
            showErrorMessage(getString(R.string.account_verified));
        } else
            startActivityFinishAll(MainActivity.class);
    }

    @Override
    public void showErrorMessage(String message) {
        dismissProgressDialog();
        showSnackBar(binding.confirmationCoordinator, message);
    }

    @Override
    public void goToOrderStatus(Order order) {
        dismissProgressDialog();
        FishDayStorage.saveOrder(order);
        startActivity(OrderStatusActivity.class, true);
//        activity.showSnackBar(binding.completeOrderCoordinator, responseEntity.getMessage());
    }

    @Override
    public void openPaymentLink(ResponseEntity<Order> responseEntity) {
        String url = responseEntity.getTarget_url();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
