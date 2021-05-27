package com.anas.fishday.screens.login;

//import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.anas.fishday.BR;
import com.anas.fishday.R;
import com.anas.fishday.base.FishDayActivity;
import com.anas.fishday.databinding.ActivityLoginBinding;
import com.anas.fishday.entities.User;
import com.anas.fishday.screens.login.interfaces.LoginInteractor;
import com.anas.fishday.screens.login.interfaces.LoginPresenter;
import com.anas.fishday.screens.main.activity.MainActivity;
import com.anas.fishday.storage.FishDayStorage;
import com.anas.fishday.system.SystemPermissions;

public class LoginActivity extends FishDayActivity implements LoginInteractor, View.OnClickListener{

    private ActivityLoginBinding binding;
    private LoginPresenter presenter;
    private String mobileNumber;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setVariable(BR.onClickListener, this);
        binding.executePendingBindings();
        presenter = new LoginPresenterImpl(this);
    }

    private boolean isValidInput() {
        if (!binding.mobileNumberEt.getText().toString().isEmpty() &&
                !binding.mobileNumberEt.getText().toString().isEmpty()) {
            return true;
        } else {
            showSnackBar(binding.loginCoordinator, R.string.error_all_fields_required);
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginBtn:
                if (isValidInput()){
                    login();
                }
                break;
            case  R.id.goToMarketBtn:
                startActivity(MainActivity.class, true);
                break;
        }
    }

    private void login() {
        showProgressDialog();
        mobileNumber = "+2" + binding.mobileNumberEt.getText().toString();
        password = binding.passwordEt.getText().toString();
        User user = new User();
        user.setMobileNumber(mobileNumber);
        user.setPassword(password);
        Log.d("5555555555555", mobileNumber);
        presenter.login(user);
    }

    @Override
    public void goToMainScreen(User user) {
        dismissProgressDialog();
        FishDayStorage.setUser(user);
        startActivity(MainActivity.class, true);
    }

    @Override
    public void showErrorMessage(String message) {
        dismissProgressDialog();
        showSnackBar(binding.loginCoordinator, message);
    }

    @Override
    protected void onStop() {
        super.onStop();
        dismissProgressDialog();
    }
}
