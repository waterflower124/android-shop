package com.anas.fishday.screens.login;

import com.anas.fishday.app.FishDayApplication;
import com.anas.fishday.base.BaseModel;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.entities.User;
import com.anas.fishday.network.AppService;
import com.anas.fishday.screens.login.interfaces.LoginPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Anas on 2/24/2018.
 */

public class LoginModel extends BaseModel {
    private LoginPresenter presenter;
    private AppService appService;
    private CompositeDisposable compositeDisposable;

    public LoginModel(LoginPresenter presenter) {
        this.presenter = presenter;
        initApiCall();
        compositeDisposable = new CompositeDisposable();
    }

    private void initApiCall() {
        appService = FishDayApplication.getWebServiceComponent().getAppService();
    }

    public void login(User user) {
        Disposable disposable = appService.login(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onLoginSuccess, this::onLoginFailure);

        compositeDisposable.add(disposable);
    }

    private void onLoginSuccess(ResponseEntity<User> responseEntity) {
        presenter.onLoginSuccess(responseEntity);
    }

    private void onLoginFailure(Throwable throwable) {
        presenter.onLoginFailure(throwable.getMessage());
    }

    @Override
    protected void clearDisposable() {
        compositeDisposable.clear();
    }
}
