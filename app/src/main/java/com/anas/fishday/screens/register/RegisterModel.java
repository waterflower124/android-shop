package com.anas.fishday.screens.register;

import com.anas.fishday.app.FishDayApplication;
import com.anas.fishday.base.BaseModel;
import com.anas.fishday.entities.BaseEntity;
import com.anas.fishday.entities.City;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.entities.User;
import com.anas.fishday.network.AppService;
import com.anas.fishday.screens.register.interfaces.RegisterPresenter;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Anas on 2/24/2018.
 */

public class RegisterModel extends BaseModel {

    private RegisterPresenter presenter;
    private AppService appService;
    private CompositeDisposable compositeDisposable;

    public RegisterModel(RegisterPresenter presenter) {
        this.presenter = presenter;
        initApiCall();
        compositeDisposable = new CompositeDisposable();
    }

    private void initApiCall() {
        appService = FishDayApplication.getWebServiceComponent().getAppService();
    }

    public void register(User user) {
        Disposable disposable = appService.register(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRegisterSuccess, this::onRegisterFailure);

        compositeDisposable.add(disposable);
    }

    private void onRegisterSuccess(ResponseEntity<User> responseEntity) {
        presenter.onRegisterSuccess(responseEntity);
    }

    private void onRegisterFailure(Throwable throwable) {
        presenter.onRegisterFailure(throwable.getMessage());
    }

    public void registerGuest(User user) {
        Disposable disposable = appService.registerGuest(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRegisterGuestSuccess, this::onRegisterGuestFailure);

        compositeDisposable.add(disposable);
    }

    private void onRegisterGuestSuccess(ResponseEntity<User> responseEntity) {
        presenter.onRegisterGuestSuccess(responseEntity);
    }

    private void onRegisterGuestFailure(Throwable throwable) {
        presenter.onRegisterGuestFailure(throwable.getMessage());
    }

    public void getCities() {
        Disposable disposable = appService.getCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGettingCitiesSuccess, this::onGettingCitiesFailure);

        compositeDisposable.add(disposable);
    }

    private void onGettingCitiesSuccess(ResponseEntity<List<City>> responseEntity) {
        presenter.onGettingCitiesSuccess(responseEntity);
    }

    private void onGettingCitiesFailure(Throwable throwable) {
        presenter.onGettingCitiesFailure(throwable.getMessage());
    }

    @Override
    protected void clearDisposable() {
        compositeDisposable.clear();
    }
}
