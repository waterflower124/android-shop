package com.anas.fishday.screens.main.fragments.completeorder;

import android.util.Log;

import com.anas.fishday.app.FishDayApplication;
import com.anas.fishday.base.BaseModel;
import com.anas.fishday.entities.AddressResponse;
import com.anas.fishday.entities.Order;
import com.anas.fishday.entities.OrderNew;
import com.anas.fishday.entities.ResponseEntity;
import com.anas.fishday.network.AppService;
import com.anas.fishday.network.geocoder.GeoCoderApi;
import com.anas.fishday.network.geocoder.GeoCoderService;
import com.anas.fishday.screens.main.fragments.completeorder.interfaces.CompleteOrderPresenter;
import com.anas.fishday.utils.Constant;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Anas on 3/9/2018.
 */

public class CompleteOrderModel extends BaseModel {

    private CompleteOrderPresenter presenter;
    private CompositeDisposable disposable;
    private AppService appService;

    public CompleteOrderModel(CompleteOrderPresenter presenter) {
        this.presenter = presenter;
        appService = FishDayApplication.getWebServiceComponent().getAppService();
        disposable = new CompositeDisposable();
    }

    public void getAddress(double lat, double lon) {
        String latlng = lat + "," + lon;
        GeoCoderService service = GeoCoderApi.getInstance();
        Disposable addressDisposable = service.getAddress(latlng, Constant.GEO_MAPS_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::OnGettingAddressSuccess, this::onGettingAddressError);

        disposable.add(addressDisposable);
    }

    private void onGettingAddressError(Throwable throwable) {
        presenter.onGettingAddressFailed(throwable.getMessage());
    }

    private void OnGettingAddressSuccess(AddressResponse addressResponse) {
        presenter.onGettingAddressSucceed(addressResponse);
    }

    public void completeOrder(OrderNew orderNew) {
        Disposable completeOrderDisposable = appService.completeOrder(orderNew)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::OnCompletingOrderSuccess, this::onCompletingOrderError);

        disposable.add(completeOrderDisposable);
    }

    private void onCompletingOrderError(Throwable throwable) {
        presenter.onCompleteOrderFailed(throwable.getMessage());
    }

    private void OnCompletingOrderSuccess(ResponseEntity<Order> responseEntity) {
        presenter.onCompleteOrderSucceed(responseEntity);
    }
    @Override
    protected void clearDisposable() {
        disposable.clear();
    }
}
