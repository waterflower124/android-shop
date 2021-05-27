package com.anas.fishday.screens.register;

//import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import androidx.databinding.DataBindingUtil;

import com.anas.fishday.BR;
import com.anas.fishday.R;
import com.anas.fishday.base.FishDayActivity;
import com.anas.fishday.databinding.ActivityRegisterBinding;
import com.anas.fishday.entities.City;
import com.anas.fishday.entities.User;
import com.anas.fishday.screens.confirmregister.ConfirmationActivity;
import com.anas.fishday.screens.main.activity.MainActivity;
import com.anas.fishday.screens.register.interfaces.RegisterInteractor;
import com.anas.fishday.screens.register.interfaces.RegisterPresenter;
import com.anas.fishday.storage.FishDayStorage;
import com.anas.fishday.utils.Constant;
import com.anas.fishday.utils.Utils;
import com.google.gson.Gson;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends FishDayActivity implements RegisterInteractor, View.OnClickListener {

    private ActivityRegisterBinding binding;
    private RegisterPresenter presenter;
    private String mobileNumber;
    private Integer cityId;
    private List<City> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setVariable(BR.onClickListener, this);
        presenter = new RegisterPresenterImpl(this);
        showProgressDialog();
        presenter.getCities();

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();
        status.getPermissionStatus().getEnabled();

        status.getSubscriptionStatus().getSubscribed();
        status.getSubscriptionStatus().getUserSubscriptionSetting();

        FishDayStorage.saveDeviceToken(status.getSubscriptionStatus().getUserId());
        status.getSubscriptionStatus().getPushToken();
    }

    private void register() {
        showProgressDialog();
        Utils.hideKeyboard(getCurrentFocus(), this);
        String number = binding.mobileNumberEt.getText().toString();
        if (number.startsWith("0")) {
            number = number.substring(1);
        }
        mobileNumber = "+966" + number;
        cityId = cities.get(binding.citiesSpinner.getSelectedItemPosition()).getId();
        User user = new User();
        user.setMobileNumber(mobileNumber);
        user.setCityId(cityId);
        user.setDevice_token(FishDayStorage.getDeviceToken());
        presenter.register(user);
    }

    private void registerGuest() {
        showProgressDialog();
        User user = new User();
        user.setDevice_token(FishDayStorage.getDeviceToken());
        presenter.registerGuest(user);
    }

    private boolean isValidInput() {
        if (!binding.mobileNumberEt.getText().toString().isEmpty()) {
            return true;
        } else {
            showSnackBar(binding.registerCoordinator, R.string.error_all_fields_required);
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerBtn:
                if (isValidInput()) {
                    register();
                }
                break;
            case R.id.goToMarketBtn:
                registerGuest();

                break;
        }
    }

    @Override
    public void goToConfirmationScreen(User user) {
        dismissProgressDialog();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.CONFIRMATION_CODE, user.getConfirmationCode());
        bundle.putString(Constant.MOBILE_NUMBER, user.getMobileNumber());
        bundle.putBoolean(Constant.IS_GUEST, false);
        startActivity(ConfirmationActivity.class, bundle, true);
    }

    @Override
    public void showErrorMessage(String message) {
        showSnackBar(binding.registerCoordinator, message);
        dismissProgressDialog();
    }

    @Override
    public void goToMain(User user) {
        Log.e("444444444444444", new Gson().toJson(user));
        dismissProgressDialog();
        FishDayStorage.setUser(user);
        startActivity(MainActivity.class, true);
    }

    @Override
    public void viewCities(List<City> cities) {
        dismissProgressDialog();
        List<String> cityNames = new ArrayList<>();
        this.cities = cities;
        for (int i = 0; i < cities.size(); i++) {
            cityNames.add(cities.get(i).getName());
        }
        SpinnerAdapter adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, cityNames);
        binding.citiesSpinner.setAdapter(adapter);
    }
}